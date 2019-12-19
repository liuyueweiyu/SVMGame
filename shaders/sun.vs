#version 330
layout (location=0) in vec3 inpos;
layout (location=1) in vec2 intex;
out vec4 expos;
out vec2 extex;
uniform mat4 MVPmat;
void main()
{
    expos = MVPmat * vec4(inpos, 1);
    extex = intex; 
    gl_Position = expos; 
}