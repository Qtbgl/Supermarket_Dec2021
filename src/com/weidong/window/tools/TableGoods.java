package com.weidong.window.tools;

import com.weidong.entity.Goods;
import com.weidong.entity.Makeup;
import com.weidong.window.SupermarketJFrame;

import javax.swing.*;
import java.util.*;

public class TableGoods {
    //外部操作所依赖的类
    OneSale oneSale;

    JTable goodsTable;

    //构造强制
    public TableGoods(OneSale oneSale, JTable goodsTable) {
        this.oneSale = oneSale;
        this.goodsTable = goodsTable;
    }

    //list存放未遗弃的所有货品。
    List<Goods> list;

    //与table上陈列的数据实时的下标和行匹配
    //辅助功能，打印到主表
    //因此需要一些值，如组成数量，复杂的状态，要让goods关联sale。
    //但是它不允许调用数据库

    //用于方便查找goods，与list伴生。
    private Map<Integer, Goods> map;

    public void setList(List<Goods> list) {
        this.list = list;
        if (list == null) {
            map = null;  //同释放
        } else {
            map = new HashMap<>();  //同载入
            for (Goods goods : list) {
                map.put(goods.getId(), goods);
            }
        }
    }

    //判断货品是否缺货，对于其关联的任一商品。
    private static boolean isShortGoods(Goods goods) {
        Set<Goods.Node> compose = goods.getComposeSale();
        int allGoodsN = 0;
        for (Goods.Node node : compose) {
            allGoodsN += node.getN();
        }
        return goods.getC() > allGoodsN;
    }

    public void printToTable() {
        Set<Goods> ignoreSet = new HashSet<>();
        int i = 0; //用于输出计数。
        //如果是初始状态，则没有在侧树上进行任何选择，oneSale就没有东西。
        if (!oneSale.isEmpty()) {
            //额外输出别选中的商品，后面有的就不输出了。
            Set<Makeup.Node> makeup = oneSale.getGoodsMakeup(); //被选中的货品一定在第一个上。
            for (Makeup.Node node : makeup) {
                String valueAt4 = "";
                Goods goods = this.map.get(node.getGoods().getId());
                if (goods != null) {  //在输出的货品表中有
                    ignoreSet.add(goods); //下面要查
                    valueAt4 = TableGoods.isShortGoods(goods) ? "缺货中" : "充足";
                } else {
                    valueAt4 = "已遗弃";
                }
                goodsTable.setValueAt(node.getGoods().getName(), i, 0);
                goodsTable.setValueAt(node.getGoods().getType(), i, 1);
                goodsTable.setValueAt(node.getGoods().getC(), i, 2);
                goodsTable.setValueAt(node.getN(), i, 3); //这一列值下面没有
                goodsTable.setValueAt(valueAt4, i, 4);
                i++;
            }
        }

        //继续输出商品选中组中，没有的货品。
        while (i < list.size() && i < SupermarketJFrame.TABLE_ROWS) {
            Goods goods = list.get(i);
            if (!ignoreSet.contains(goods)) {
                String valueAt4 = TableGoods.isShortGoods(goods) ? "缺货中" : "充足";
                goodsTable.setValueAt(goods.getName(), i, 0);
                goodsTable.setValueAt(goods.getType(), i, 1);
                goodsTable.setValueAt(goods.getC(), i, 2);
                goodsTable.setValueAt("无", i, 3);
                goodsTable.setValueAt(valueAt4, i, 4);
                i++;
            }
        }
    }

}