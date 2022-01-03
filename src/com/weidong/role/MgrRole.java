package com.weidong.role;

import com.weidong.biz.CustomerBiz;
import com.weidong.biz.GoodsBiz;
import com.weidong.biz.SaleBiz;
import com.weidong.entity.*;
import com.weidong.exception.biz.AlreadyExistedAddException;
import com.weidong.exception.biz.IdNotFoundException;
import com.weidong.exception.biz.ItemCountException;
import com.weidong.exception.biz.ValueUnreasonException;
import com.weidong.role.superclass.Role;

import java.util.List;

public class MgrRole extends Role {
    public MgrRole(CustomerBiz customerBiz, GoodsBiz goodsBiz, SaleBiz saleBiz) {
        super(customerBiz, goodsBiz, saleBiz);
    }

    //进口已有的货品，需要id
    public void importOldGoods(Import _import) throws ItemCountException, IdNotFoundException {
        goodsBiz.importOld(_import);
    }
    //进口新的货品，不需要id
    public void importGoods(Import _import) throws AlreadyExistedAddException, ItemCountException {
        goodsBiz.importNew(_import);
    }
    //查看货品进口记录，需要id
    public List<Import> seeGoodsImportRecords(Goods goods){
        return goodsBiz.seeGoodsImportRecords(goods);
    }
    //查看超市所有进口记录
    public List<Import> seeImportRecords(){
        return goodsBiz.seeAllImportRecords();
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
        goodsBiz.forbid(goods);
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
    public void modifyGoods_etc(Goods goods) throws AlreadyExistedAddException, IdNotFoundException {
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

    /*以上是超市管理员的货品业务*/

    //新增商品，不需要id
    public void createSale(Sale sale) throws ItemCountException, ValueUnreasonException, IdNotFoundException {
        saleBiz.createSale(sale);
    }
    //查看超市所有商品，上架中的。
    public List<Sale> seeSale(){
        return saleBiz.seeNowSale();
    }
    //查看指定商品，需要id
    public Sale seeSaleById(int id){
        return saleBiz.seeSaleById(id);
    }
    //查看某商品的最新代。可以是迭代品。新代无论下架。
    public Sale seeFrontSaleBySaleId(int id) throws IdNotFoundException{
        return saleBiz.seeFrontSaleBySaleId(id);
    }
    //搜索某名称的商品
    public List<Sale> searchSaleLikeName(String info){
        return saleBiz.searchSaleLikeName(info);
    }
    //修改商品组合，需要id
    public void modifySaleMakeup(int saleId, Makeup saleMakeup) throws ItemCountException, IdNotFoundException {
        saleBiz.modifySaleMakeup(saleId,saleMakeup);
    }
    //修改商品价格，需要id
    public void modifySalePrice(Sale sale) throws ValueUnreasonException, IdNotFoundException {
        saleBiz.modifySalePrice(sale);
    }
    //修改商品其他，需要id
    public void modifySaleName(Sale sale) throws ValueUnreasonException, IdNotFoundException {
        saleBiz.modifySaleName(sale);
    }
    //**统计商品的修改记录**
    public List<Sale> getSaleModifyRecords(Sale sale) throws IdNotFoundException {
        return saleBiz.analysePastSales(sale);
    }
    //**统计商品的购买情况**，任何的商品。
    public List<Purchase> getSalePurchaseRecords(Sale sale) throws IdNotFoundException {
        return saleBiz.analyseSalePurchases(sale);
    }
    //获取所有购买记录，正上架的商品。
    public List<Purchase> seePurchase(){
        return saleBiz.seeNowPurchase();
    }
    //获取某天的购买记录
    /*public List<Purchase> seePurchaseByDate(Date date){
        return saleBiz.seePurchaseByDate(date);
    }*/
    //**下架商品**
    public void removeSale(Sale sale) throws IdNotFoundException {
        saleBiz.remove(sale);
    }
    //查看下架的商品
    public List<Sale> seeRemovedSale(){
        return saleBiz.seeRemovedSale();
    }
    //**撤回下架的商品**
    public void recoverRemovedSale(Sale sale) throws IdNotFoundException {
        saleBiz.recover(sale);
    }

    /*以上是超市管理员的商品业务*/

    //**统计以往的购买记录**
    public List<Purchase> getCustomerPurchaseRecords(Customer customer){
        return customerBiz.analysePurchases(customer);
    }
    //**统计购买过的商品**
    public List<Sale> getCustomerSaleRecords(Customer customer){
        return customerBiz.analyseSales(customer);
    }
    //**恢复注销的用户**
    public void recoverCustomer(Customer customer) throws IdNotFoundException {
        customerBiz.recover(customer);
    }
}
