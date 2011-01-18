package org.cloudme.gaestripes;

import java.util.Iterator;
import java.util.List;

import com.google.appengine.api.datastore.Transaction;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

public abstract class AbstractDao<T> {
    public static QueryOperator filter(final String condition, final Object value) {
        return new QueryOperator() {
            @Override
            public <T> Query<T> apply(Query<T> query) {
                return query.filter(condition, value);
            }
        };
    }

    public static QueryOperator orderBy(final String condition) {
        return new QueryOperator() {
            @Override
            public <T> Query<T> apply(Query<T> query) {
                return query.order(condition);
            }
        };
    }

    protected abstract class Callback<R> {
        private final boolean needsTransaction;

        public Callback() {
            this(false);
        }

        public Callback(boolean needsTransaction) {
            this.needsTransaction = needsTransaction;
        }

        protected abstract R execute(Objectify ofy);

        boolean isNeedsTransaction() {
            return needsTransaction;
        }
    }

    protected final Class<T> baseClass;

    public AbstractDao(Class<T> baseClass) {
        this.baseClass = baseClass;
    }

    public void delete(final Long id) {
        delete(new Key<T>(baseClass, id));
    }

    private void delete(final Key<T> key) {
        execute(new Callback<Object>(true) {
            @Override
            public Object execute(Objectify ofy) {
                ofy.delete(key);
                return null;
            }
        });
    }

    public void deleteAll(QueryOperator... operators) {
        for (Key<T> key : ((Query<T>) findAll(operators)).fetchKeys()) {
            delete(key);
        }
    }

    protected <R> R execute(Callback<R> callback) {
        Objectify ofy = callback.needsTransaction ? ObjectifyService
                .beginTransaction() : ObjectifyService.begin();
        Transaction txn = ofy.getTxn();
        try {
            R result = callback.execute(ofy);
            if (isActive(txn)) {
                txn.commit();
            }
            return result;
        }
        finally {
            if (isActive(txn)) {
                txn.rollback();
            }
        }
    }

    private boolean isActive(Transaction txn) {
        return txn != null && txn.isActive();
    }

    public T find(final Long id) {
        return execute(new Callback<T>() {
            @Override
            protected T execute(Objectify ofy) {
                return ofy.find(baseClass, id);
            }
        });
    }

    public T findSingle(String condition, Object value) {
        Iterator<T> it = findAll(filter(condition, value)).iterator();
        T result = null;
        if (it.hasNext()) {
            result = it.next();
            if (it.hasNext()) {
                throw new IllegalStateException(String.format("Multiple objects found for class %s with (%s, %s)",
                        baseClass.getName(),
                        condition,
                        value));
            }
        }
        return result;
    }

    public List<T> listAll(final QueryOperator... operators) {
        return ((Query<T>) findAll(operators)).list();
    }

    public final Iterable<T> findAll(final QueryOperator... operators) {
        return execute(new Callback<Iterable<T>>() {
            @Override
            protected Iterable<T> execute(Objectify ofy) {
                Query<T> query = ofy.query(baseClass);
                for (QueryOperator operator : operators) {
                    query = operator.apply(query);
                }
                return query;
            }
        });
    }

    public void save(final Iterable<? extends T> objs) {
        execute(new Callback<Object>() {
            @Override
            protected Object execute(Objectify ofy) {
                ofy.put(objs);
                return null;
            }
        });
    }

    public void save(final T t) {
        execute(new Callback<Object>(true) {
            @Override
            protected Object execute(Objectify ofy) {
                ofy.put(t);
                return null;
            }
        });
    }
}