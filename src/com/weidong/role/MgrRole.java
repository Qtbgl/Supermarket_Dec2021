package com.weidong.role;

import com.weidong.entity.*;
import com.weidong.exception.AddAlreadyExistedException;
import com.weidong.exception.IdNotFoundException;
import com.weidong.exception.ItemCountException;
import com.weidong.role.superclass.Role;

import java.util.Date;
import java.util.List;

public class MgrRole extends Role {
    //进口已有的货品，需要id
    public void importOldGoods(Import _import) throws ItemCountException, IdNotFoundException {
        goodsBiz.importOld(_import);
    }
    //进口新的货品，不需要id
    public void importGoods(Import _import) throws AddAlreadyExistedException, ItemCountException {
        goodsBiz.importNew(_import);
    }
    //查看货品进口记录，需要id
    public List<Import> seeGoodsImportRecords(Goods goods){
        return goodsBiz.seeGoodsImportRecords(goods);
    }
    //查看超市所有进口记录
    public List<Import> seeImportRecords(){
        return goodsBiz.seeImportRecords();
    }
    //查看超市所有货品
    public List<Goods> seeGoods(){
        return goodsBiz.seeGoods();
    }
    //搜索货品，通过名称，类型
    public List<Goods> searchGoodsLike(String info){
        return goodsBiz.searchGoodsLike(info);
    }
    //搜索货品，通过名称
    public List<Goods> searchGoodsLikeName(String info){
        return goodsBiz.searchGoodsLikeName(info);
    }
    //搜索货品，通过类型
    public List<Goods> searchGoodsLikeType(String info){
        return goodsBiz.searchGoodsLikeType(info);
    }
    //查看货品，需要id
    public Goods seeGoodsById(Goods goods){
        return goodsBiz.seeGoodsById(goods);
    }
    //**遗弃货品**
    public void forbidGoods(Goods goods) throws ItemCountException, IdNotFoundException {
        goodsBiz.remove(goods);
    }
    //查看遗弃的货品
    public List<Goods> seeForbiddenGoods(){
        return goodsBiz.seeForbiddenGoods();
    }
    //**撤回遗弃的货品**
    public void recoverGoods(Goods goods){
        goodsBiz.recover(goods);
    }
    //撤下商品，不遗弃只减少数量，同时增加撤下记录
    public void removeGoods(Remove remove) throws IdNotFoundException, ItemCountException{
        goodsBiz.removeGoods(remove);
    }
    //查看某商品的撤下记录，需要id
    public List<Remove> seeGoodsRemove(Goods goods){
        return goodsBiz.seeGoodsRemove(goods);
    }
    //查看超市所有的撤下记录
    public List<Remove> seeRemove(){
        return goodsBiz.seeRemove();
    }
    //修改货品其他，需要id
    public void modifyGoods_etc(Goods goods) throws AddAlreadyExistedException, IdNotFoundException {
        goodsBiz.modifyGoods_etc(goods);
    }
    //**统计货品相关商品**
    public List<Sale> getRelatedSale(Goods goods){
        return goodsBiz.analyseSales(goods);
    }
    //**统计货品的购买情况**
    public List<Purchase> getRelatePurchase(Goods goods){
        return goodsBiz.analysePurchases(goods);
    }
    //新增商品，不需要id
    public void createSale(Sale sale){
        saleBiz.createSale(sale);
    }
    //查看超市所有商品
    public List<Sale> seeSale(){
        return saleBiz.seeSale();
    }
    //查看指定商品，需要id
    public Sale seeSaleById(Sale sale){
        return saleBiz.seeSaleById(sale);
    }
    //搜索某名称的商品
    public List<Sale> searchSaleLikeName(String info){
        return saleBiz.searchSaleLikeName(info);
    }
    //搜索含某货品的商品，需要id
    public List<Sale> searchSaleByGood(Goods goods){
        return saleBiz.searchSaleByGood(goods);
    }
    //修改商品组合，需要id
    public void modifySaleMakeup(Sale sale){
        saleBiz.modifySaleMakeup(sale);
    }
    //修改商品价格，需要id
    public void modifySalePrice(Sale sale){
        saleBiz.modifySalePrice(sale);
    }
    //修改商品其他，需要id
    public void modifySale_etc(Sale sale){
        saleBiz.modifySale_etc(sale);
    }
    //**统计商品的修改记录**
    public List<Sale> getSaleModifyRecords(Sale sale){
        return saleBiz.analyseSales(sale);
    }
    //**统计商品的购买情况**
    public List<Purchase> getSalePurchaseRecords(Sale sale){
        return saleBiz.analysePurchases(sale);
    }
    //获取所有购买记录
    public List<Purchase> seePurchase(){
        return saleBiz.seePurchase();
    }
    //获取某天的购买记录
    public List<Purchase> seePurchaseByDate(Date date){
        return saleBiz.seePurchaseByDate(date);
    }
    //**下架商品**
    public void removeSale(Sale sale){
        saleBiz.remove(sale);
    }
    //查看下架的商品
    public List<Sale> seeRemovedSale(){
        return saleBiz.seeRemovedSale();
    }
    //**撤回下架的商品**
    public void recoverRemovedSale(Sale sale){
        saleBiz.recover(sale);
    }
    //**统计以往的购买记录**
    public List<Purchase> getCustomerPurchaseRecords(Customer customer){
        return customerBiz.analysePurchases(customer);
    }
    //**统计购买过的商品**
    public List<Sale> getCustomerSaleRecords(Customer customer){
        return customerBiz.analyseSales(customer);
    }
    //**恢复注销的用户**
    public void recoverCustomer(Customer customer){
        customerBiz.recover(customer);
    }
}
