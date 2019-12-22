#version 330 core
layout(points) in ;
layout(triangle_strip, max_vertices = 4) out;
out float t;
out vec2 pos;
uniform mat4 P;

void main()
{	
	float x = 0.4;
    t = gl_in[0].gl_PointSize;
    gl_Position = P*(gl_in[0].gl_Position+vec4(-x,-x,0,0));
    pos = vec2(0,0);
    EmitVertex();
    gl_Position = P*(gl_in[0].gl_Position+vec4(-x,x,0,0));
    pos = vec2(0,1);
    EmitVertex();
    gl_Position = P*(gl_in[0].gl_Position+vec4(x,-x,0,0));
    pos = vec2(1,0);
    EmitVertex();
    gl_Position = P*(gl_in[0].gl_Position+vec4(x,x,0,0));
    pos = vec2(1,1);
    EmitVertex();
    EndPrimitive();
}