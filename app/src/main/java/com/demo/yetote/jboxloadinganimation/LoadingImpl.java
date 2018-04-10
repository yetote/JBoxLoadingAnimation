package com.demo.yetote.jboxloadinganimation;

import android.view.View;

import org.jbox2d.collision.ContactID;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * com.demo.yetote.jboxloadinganimation
 *
 * @author Swg
 * @date 2018/4/10 10:47
 */
public class LoadingImpl {
    /**
     * 世界
     */
    private World mWorld;
    /**
     * 密度
     */
    private float mDensity;
    /**
     * 质量
     */
    private float quality;
    /**
     * 摩擦力系数
     */
    private float mFriction = 0.5f;
    /**
     * 弹力系数
     */
    private float mRestitution = 0.5f;
    /**
     * 比例
     */
    private int ratio = 50;
    private int width, height;

    public LoadingImpl() {

    }

    public void createWorld() {

        /*
         创建世界
         @params vec2 重力向量
         */
        mWorld = new World(new Vec2(0, 10f));

        createVerticalRounds();
        createHorizontalRounds();

    }

    public void setWorldSize(int x,int y){
        this.width = (int) trueToImitate(x);
        this.height = (int) trueToImitate(y);
    }

    public void onSizeChange(int w, int h) {

    }

    public void startWorld(){
        if (mWorld!=null){

        }
    }

    /**
     * 创建刚体
     *
     * @param v 刚体View
     */
    public void createBody(View v) {
        float boxWight = trueToImitate(v.getWidth());
        float boxHeight = trueToImitate(v.getHeight());

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;

        PolygonShape rectBox = new PolygonShape();
        rectBox.setAsBox(boxWight / 2, boxHeight / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectBox;
        fixtureDef.density = mDensity;
        fixtureDef.friction = mFriction;
        fixtureDef.restitution = mRestitution;

        bodyDef.position.set(trueToImitate(v.getX() + v.getWidth() / 2),
                trueToImitate(v.getY() + v.getHeight() / 2));
        Body rectBody = mWorld.createBody(bodyDef);
        rectBody.createFixture(fixtureDef);
        v.setTag(R.id.body_tag);
    }

    /**
     * 垂直方向的刚体，指屏幕左右边界
     */
    private void createVerticalRounds() {
        //真实转模拟
        float boxWight = trueToImitate(ratio);
        float boxHeight = trueToImitate(height);

        //创建刚体描述
        BodyDef bodyDef = new BodyDef();
        //刚体为静态
        bodyDef.type = BodyType.STATIC;

        //屏幕左侧边界的刚体，为多边形刚体
        PolygonShape box = new PolygonShape();
        //将多边形设置为矩形
        box.setAsBox(boxWight, boxHeight);

        //增加物理属性
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = mDensity;
        fixtureDef.friction = mFriction;
        fixtureDef.restitution = mRestitution;

        //描述刚体摆放位置为左侧
        bodyDef.position.set(-boxWight, boxHeight);
        Body leftBody = mWorld.createBody(bodyDef);
        leftBody.createFixture(fixtureDef);

        //描述刚体摆放位置为右侧
        bodyDef.position.set(trueToImitate(width) + boxWight, boxHeight);
        Body rightBody = mWorld.createBody(bodyDef);
        rightBody.createFixture(fixtureDef);

    }

    /**
     * 水平方向刚体，指屏幕上下边界
     */
    private void createHorizontalRounds() {
        float boxWight = trueToImitate(width);
        float boxHeight = trueToImitate(ratio);

        //创建刚体描述
        BodyDef bodyDef = new BodyDef();
        //刚体为静态
        bodyDef.type = BodyType.STATIC;

        //屏幕左侧边界的刚体，为多边形刚体
        PolygonShape box = new PolygonShape();
        //将多边形设置为矩形
        box.setAsBox(boxWight, boxHeight);

        //增加物理属性
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = mDensity;
        fixtureDef.friction = mFriction;
        fixtureDef.restitution = mRestitution;

        //描述刚体摆放位置为上方
        bodyDef.position.set(0, boxHeight);
        Body topBody = mWorld.createBody(bodyDef);
        topBody.createFixture(fixtureDef);

        //描述刚体摆放位置为下方
        bodyDef.position.set(0, trueToImitate(height));
        Body bottomBody = mWorld.createBody(bodyDef);
        bottomBody.createFixture(fixtureDef);

    }

    /**
     * 模拟世界转化为真实世界
     *
     * @param imitate 模拟世界数据
     * @return 真实世界数据
     */
    private float imitateToTrue(float imitate) {
        return imitate * ratio;
    }

    /**
     * 真实世界转化模拟世界
     *
     * @param reality 真实世界数据
     * @return 模拟世界数据
     */
    private float trueToImitate(float reality) {
        return reality / ratio;
    }
}
