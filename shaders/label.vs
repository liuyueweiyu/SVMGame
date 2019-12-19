#version 330
layout (location=0) in vec3 inpos;
out vec4 expos;
uniform mat4 MVPmat;
void main()
{
    expos = MVPmat * vec4(inpos, 1);
    gl_Position = expos; 
}