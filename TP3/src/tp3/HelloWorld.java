package tp3;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.openvr.CameraVideoStreamFrameHeader;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class HelloWorld {

	// The window handle
	private long window;

	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(900, 900, "3iL - Graphic interfaces & 3D - TP3", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
	}

	private void loop() {

		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.

        // Set anti-aliazing :
        glShadeModel(GL_SMOOTH);

        // Set background default color :
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClearDepth(1.0f);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        float rotate = 0.0f;

        // Set frustum :
        float fh = 0.5f;
        float aspect = (float) 900 / (float) 900;
        float fw = fh * aspect;
        glFrustum(-fw, fw, -fh, fh, 1.0f, 1000.0f);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        
		while ( !glfwWindowShouldClose(window) ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glLoadIdentity();
            glTranslatef(0.0f, 0.0f, -5.0f);
            glRotatef(rotate, 1.0f, 0.0f, 0.0f);
            glRotatef(rotate, 0.0f, 1.0f, 0.0f);
            glRotatef(rotate, 0.0f, 0.0f, 1.0f);

            glBegin(GL_QUADS);
            //première face
            glColor3f(1.0f, 0.0f, 0.0f); // Set the color for the next drawing
            glVertex3f(-1.0f, 1.0f, 1.0f); // Set a point at top left
            glVertex3f(1.0f, 1.0f, 1.0f); // Set a point at top right
            glVertex3f(1.0f, -1.0f, 1.0f); // Set a point at bottom right
            glVertex3f(-1.0f, -1.0f, 1.0f); // Set a point at bottom left

            //deuxième face
            glColor3f(0.0f, 1.0f, 0.0f); // Set the color for the next drawing
            glVertex3f(1.0f, 1.0f, 1.0f); // Set a point at top left
            glVertex3f(1.0f, 1.0f, -1.0f); // Set a point at top right
            glVertex3f(1.0f, -1.0f, -1.0f); // Set a point at bottom right
            glVertex3f(1.0f, -1.0f, 1.0f); // Set a point at bottom left

            //troisième face
            glColor3f(0.0f, 0.0f, 1.0f); // Set the color for the next drawing
            glVertex3f(-1.0f, 1.0f, -1.0f); // Set a point at top left
            glVertex3f(1.0f, 1.0f, -1.0f); // Set a point at top right
            glVertex3f(1.0f, 1.0f, 1.0f); // Set a point at bottom right
            glVertex3f(-1.0f, 1.0f, 1.0f); // Set a point at bottom left

            //quatrième face
            glColor3f(0.5f, 0.0f, 0.5f); // Set the color for the next drawing
            glVertex3f(-1.0f, -1.0f, -1.0f); // Set a point at top left
            glVertex3f(1.0f, -1.0f, -1.0f); // Set a point at top right
            glVertex3f(1.0f, -1.0f, 1.0f); // Set a point at bottom right
            glVertex3f(-1.0f, -1.0f, 1.0f); // Set a point at bottom left

            //cinquième face
            glColor3f(0.5f, 0.5f, 0.0f); // Set the color for the next drawing
            glVertex3f(-1.0f, 1.0f, 1.0f); // Set a point at top left
            glVertex3f(-1.0f, 1.0f, -1.0f); // Set a point at top right
            glVertex3f(-1.0f, -1.0f, -1.0f); // Set a point at bottom right
            glVertex3f(-1.0f, -1.0f, 1.0f); // Set a point at bottom left

            //sixième face
            glColor3f(1.0f, 1.0f, 1.0f); // Set the color for the next drawing
            glVertex3f(-1.0f, 1.0f, -1.0f); // Set a point at top left
            glVertex3f(1.0f, 1.0f, -1.0f); // Set a point at top right
            glVertex3f(1.0f, -1.0f, -1.0f); // Set a point at bottom right
            glVertex3f(-1.0f, -1.0f, -1.0f); // Set a point at bottom left
            
            glEnd();
            
            rotate += 0.2f;

			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();


		}

	}

	public static void main(String[] args) {
		new HelloWorld().run();
	}

}