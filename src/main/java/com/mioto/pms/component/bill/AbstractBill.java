package com.mioto.pms.component.bill;

/**
 * 账单抽象类
 * @author admin
 * @date 2021-08-21 14:50
 */
public abstract class AbstractBill {

    protected boolean genMainBill = false;



    /**
     * 创建账单
     */
    public void create(Object obj){
        double totalAmount = createChildBill(obj);

        if (genMainBill){
            createMainBill();
        }else {
            updateMainBill(totalAmount);
        }

        hook();
    }


    protected void createMainBill(){

    }

    protected abstract double createChildBill(Object obj);

    protected  void updateMainBill(double totalAmount) {
    }

    protected void hook(){}
}
