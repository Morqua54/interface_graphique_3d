package com.tp5;

import com.badlogic.gdx.math.Vector3;

public class Sphere3D {

    public String name;
    public Vector3 position;
    public float raySphere;
    public Vector3 color;
    public String type;
    public float kPhong;
    public float kReflex;
    public float kRefrac;
    public float kText;

    Sphere3D(String nomGiven, float kPhongGiven, float kReflexGiven, float kRefracGiven, float kTextGiven,
            Vector3 posGiven, float rayon, Vector3 couleurGiven) {
        this.name = nomGiven;
        this.position = posGiven;
        this.raySphere = rayon;
        this.color = couleurGiven;
        this.type = "objet3D";
        this.kPhong = kPhongGiven;
        this.kReflex = kReflexGiven;
        this.kRefrac = kRefracGiven;
        this.kText = kTextGiven;
    }

}