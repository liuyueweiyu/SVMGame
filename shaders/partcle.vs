#version 330 core
layout(location = 0) in vec4 pos;
uniform mat4 V;
uniform mat4 P;
uniform vec3 cor;

void main()
{
    gl_Position = V*vec4(pos.xyz,1.0);
    gl_PointSize = pos.w;
}
