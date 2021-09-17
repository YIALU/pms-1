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
            createMainBill(totalAmount);
        }else {
            updateMainBill(totalAmount);
        }

        hook();
    }

    /**
     * 新增主账单
     */
    protected void createMainBill(double totalAmount){

    }

    /**
     * 生成子账单
     * @param obj
     * @return
     */
    protected abstract double createChildBill(Object obj);

    /**
     * 更新主账单金额
     * @param totalAmount
     */
    protected  void updateMainBill(double totalAmount) {
    }

    protected void hook(){}
}
