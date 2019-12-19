#version 330 core
layout(points) in ;
layout(triangle_strip, max_vertices = 4) out;
uniform mat4 P;

void main()
{	
	float x = 0.1;
    gl_Position = P*(gl_in[0].gl_Position+vec4(-x,-x,0,0));
    EmitVertex();
    gl_Position = P*(gl_in[0].gl_Position+vec4(-x,x,0,0));
    EmitVertex();
    gl_Position = P*(gl_in[0].gl_Position+vec4(x,-x,0,0));
    EmitVertex();
    gl_Position = P*(gl_in[0].gl_Position+vec4(x,x,0,0));
    EmitVertex();
    EndPrimitive();
}