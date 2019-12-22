package src;

import static manager.WindowManager.height;
import static manager.WindowManager.width;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix3d;
import org.joml.Matrix4d;
import org.joml.Matrix4f;
import org.joml.Quaterniond;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector4d;
import org.joml.Vector4f;

import event.Event;
import event.IEventListener;
import manager.WindowManager;
import renderer.Shader;

public class Test {

	public static void main(String[] args) {
		Vector4f v = new Vector4f(0,0,-3,1);
		Matrix4f m = new Matrix4f().perspective(1.1f, 1f * width() / height(), 0, 1000);
		System.out.println(m);
		v.mul(m);
		System.out.println(v);
	}
}
