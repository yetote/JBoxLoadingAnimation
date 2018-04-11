package com.demo.yetote.jboxloadinganimation;

import android.util.Log;
import android.view.View;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.util.Random;

/**
 * com.demo.yetote.jboxloadinganimation
 *
 * @author Swg
 * @date 2018/4/10 10:47
 */
class LoadingImpl {
    private static final String TAG = "LoadingImpl";
    /**
     * 世界
     */
    private World mWorld;
    /**
     * 密度
     */
    private float mDensity;
    /**
     * 摩擦力系数
     */
    private float mFriction = 0.5f;
    /**
     * 弹力系数
     */
    private float mRestitution = 0;
    /**
     * 比例
     */
    private int ratio = 10;
    private int width, height;
    private Random random = new Random();

    LoadingImpl(float mDensity) {
        this.mDensity = mDensity;
    }

    void createWorld() {

        /*
         创建世界
         @params vec2 重力向量
         */
        mWorld = new World(new Vec2(0, 100f));

        createVerticalRounds();
        createHorizontalRounds();

    }


    void onSizeChange(int w, int h) {
        this.width = w;
        this.height = h;
    }

    void startWorld() {
        float dt = 1f / 60f;
        //计算速度时间
        int velocityIterations = 3;
        //计算位置时间
        int positionIterations = 10;
        mWorld.step(dt, velocityIterations, positionIterations);
    }

    /**
     * 创建刚体
     *
     * @param v 刚体View
     */
    void createBody(View v) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;

        PolygonShape rectBox = new PolygonShape();
        rectBox.setAsBox(trueToImitate(v.getWidth() / 2), trueToImitate(v.getHeight() / 2));
        Log.e(TAG, "createBody: " + rectBox.getVertexCount());
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectBox;
        fixtureDef.density = mDensity;
        fixtureDef.friction = mFriction;
        fixtureDef.restitution = mRestitution;

        bodyDef.position.set(trueToImitate(v.getWidth() / 2),
                trueToImitate(v.getHeight() / 2));

        Body rectBody = mWorld.createBody(bodyDef);

        rectBody.createFixture(fixtureDef);
        v.setTag(R.id.body_tag, rectBody);
        rectBody.setLinearVelocity(new Vec2(random.nextFloat() * 100, random.nextFloat() * 100));
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
        bodyDef.position.set(trueToImitate(width) + boxWight, 0);
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
        bodyDef.position.set(0, -boxHeight);
        Body topBody = mWorld.createBody(bodyDef);
        topBody.createFixture(fixtureDef);

        //描述刚体摆放位置为下方
        bodyDef.position.set(0, trueToImitate(height) + boxHeight);
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

    float getX(View v) {
        Body body = (Body) v.getTag(R.id.body_tag);
        if (body != null) {
            return imitateToTrue(body.getPosition().x) - (v.getWidth() / 2);
        }
        return 0;
    }

    float getY(View v) {
        Body body = (Body) v.getTag(R.id.body_tag);
        if (body != null) {
            return imitateToTrue(body.getPosition().y) - (v.getHeight() / 2);
        }
        return 0;
    }

    float getViewRotate(View v) {
        Body body = (Body) v.getTag(R.id.body_tag);
        if (body != null) {
            float angle = body.getAngle();
            return (float) ((angle / Math.PI * 180) % 360);
        }
        return 0;
    }


}
