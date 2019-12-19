#version 330 core
layout(location = 0) in vec3 pos;
uniform mat4 V;
uniform mat4 P;
uniform vec3 cor;
void main()
{
    gl_Position = V*vec4(pos,1.0);
}
