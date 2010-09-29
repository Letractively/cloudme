package org.cloudme.gaestripes;

import java.util.List;

import com.google.appengine.api.datastore.Transaction;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

public abstract class BaseDao<T> {
    protected static void register(Class<?> clazz) {
        ObjectifyService.register(clazz);
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

    private final Class<T> baseClass;

    public BaseDao(Class<T> baseClass) {
        this.baseClass = baseClass;
    }

    public void delete(final Long id) {
        execute(new Callback<Object>(true) {
            @Override
            public Object execute(Objectify ofy) {
                ofy.delete(new Key<Object>(baseClass, id));
                return null;
            }
        });
    }

    protected <R> R execute(Callback<R> callback) {
        Objectify ofy = callback.needsTransaction ? ObjectifyService
                .beginTransaction() : ObjectifyService.begin();
        Transaction txn = ofy.getTxn();
        try {
            R result = callback.execute(ofy);
            if (callback.needsTransaction) {
                txn.commit();
            }
            return result;
        }
        finally {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public T find(final Long id) {
        return execute(new Callback<T>() {
            @Override
            protected T execute(Objectify ofy) {
                return ofy.find(baseClass, id);
            }
        });
    }

    public List<T> listAll() {
        return execute(new Callback<List<T>>() {
            @Override
            protected List<T> execute(Objectify ofy) {
                return ofy.query(baseClass).list();
            }
        });
    }

    public Iterable<T> findAll() {
        return findAll(null);
    }

    public Iterable<T> findAll(final String order) {
        return execute(new Callback<Iterable<T>>() {
            @Override
            protected Iterable<T> execute(Objectify ofy) {
                Query<T> query = ofy.query(baseClass);
                if (order != null) {
                    query = query.order(order);
                }
                return query;
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
