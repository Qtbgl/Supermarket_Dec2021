package com.weidong.window.tools;

import com.weidong.entity.Makeup;
import com.weidong.entity.Sale;
import com.weidong.window.SupermarketJFrame;

import javax.swing.*;
import java.util.*;

public class OneSale {
    //外部操作所依赖的类
    JTextField saleNameTextField;

    JTextField salePriceTextField;

    //构造强制
    public OneSale(JTextField saleNameTextField, JTextField salePriceTextField) {
        this.saleNameTextField = saleNameTextField;
        this.salePriceTextField = salePriceTextField;
    }

    private Sale sale;

    //正常的商品：有组成，没有购买情况。
    //用于记录商品树中选中的一个。
    //只能set，get只能克隆一个，克隆程度（goods之上）。以防止被误改
    //增加判空方法，判断有没有注入。
    //更新通过set方法。
    //封装一些辅助功能，如查找，匹配，提供给其他方法。
    //还可以有输出到组件的功能。
    //JFrame构建时创建，那时无sale。

    //用于方便查找goods，与sale伴生。
    private Map<Integer, Makeup.Node> map;

    public void setSale(Sale sale) {
        this.sale = sale;
        if (sale == null) {
            map = null; //同步释放
        } else {
            Set<Makeup.Node> makeup = sale.getSaleMakeup().getMakeup();
            map = new HashMap<>();
            for (Makeup.Node node : makeup) { //同步载入
                map.put(node.getGoods().getId(), node);
            }
        }
    }

    public boolean isEmpty() {
        return sale == null;
    }

    public boolean hasGoods(int goodsId) {
        return map.containsKey(goodsId);
    }

    //获取的set也是克隆过的。
    public Set<Makeup.Node> getGoodsMakeup() {
        Set<Makeup.Node> makeup = sale.getSaleMakeup().getMakeup();
        Set<Makeup.Node> makeup1 = new HashSet<>();
        for (Makeup.Node node : makeup) {
            Makeup.Node node1 = new Makeup.Node();
            node1.setN(node.getN());  //载入N
            node1.setGoods(node.getGoods()); //载入goods
            makeup1.add(node1);
            //node1全载入完成
        }
        return makeup1;
    }

    //克隆到Goods以上的级别
    public Sale getCopy() {
        if (sale == null) {
            return null;
        }
        Sale sale1 = new Sale();
        sale1.setId(sale.getId());
        sale1.setName(sale.getName());
        sale1.setPrice(sale.getPrice());
        sale1.setSaleMakeup(new Makeup());
        sale1.getSaleMakeup().setSale(sale1);
        sale1.getSaleMakeup().setMakeup(this.getGoodsMakeup());
        return sale1;
    }

    //下下边栏的组件上打印
    public void printToBottom() {
        saleNameTextField.setText(sale.getName());
        salePriceTextField.setText("" + sale.getPrice());
    }

}
