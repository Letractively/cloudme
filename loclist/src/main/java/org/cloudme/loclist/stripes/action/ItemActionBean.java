package org.cloudme.loclist.stripes.action;

import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import org.cloudme.gaestripes.AbstractActionBean;
import org.cloudme.loclist.item.ItemService;
import org.cloudme.loclist.item.ListItem;
import org.cloudme.loclist.model.Item;
import org.cloudme.loclist.model.ItemList;

import com.google.inject.Inject;

@UrlBinding( "/action/item/{itemListId}/{$event}/{itemId}/{attribute}" )
public class ItemActionBean extends AbstractActionBean {
    @Inject
    private ItemService itemService;
    private Long itemId;
    private Long itemListId;
    @ValidateNestedProperties( { @Validate( field = "text", required = true ) } )
    private Item item;
    private String attribute;
    private List<ListItem> listItems;
    private ItemList itemList;
    
    @DontValidate
    @DefaultHandler
    public Resolution index() {
        listItems = itemService.getListItems(itemListId);
        itemList = itemService.getItemList(itemListId);
        return resolve("itemIndex.jsp");
    }

    public Resolution create() {
        itemService.createItem(itemListId, item, attribute);
        return new RedirectResolution("/action/item/" + itemListId);
    }

    @DontValidate
    public Resolution delete() {
        itemService.deleteItem(itemId);
        return new RedirectResolution("/action/item/" + itemListId);
    }

    @DontValidate
    public Resolution add() {
        itemService.addToList(itemListId, itemId, attribute);
        return new RedirectResolution("/action/item/" + itemListId);
    }

    @DontValidate
    public Resolution remove() {
        itemService.removeFromList(itemListId, itemId);
        return new RedirectResolution("/action/item/" + itemListId);
    }

    @DontValidate
    public Resolution update() {
        itemService.addToList(itemListId, itemId, attribute);
        return null;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long id) {
        this.itemId = id;
    }

    public Long getItemListId() {
        return itemListId;
    }

    public void setItemListId(Long itemListId) {
        this.itemListId = itemListId;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setListItems(List<ListItem> listItems) {
        this.listItems = listItems;
    }

    public List<ListItem> getListItems() {
        return listItems;
    }

    public void setItemList(ItemList itemList) {
        this.itemList = itemList;
    }

    public ItemList getItemList() {
        return itemList;
    }
}