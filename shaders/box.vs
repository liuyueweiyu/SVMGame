#version 330
layout (location=0) in vec3 inpos;
layout (location=1) in vec2 intex;
layout (location=2) in vec3 innor;
layout (location=3) in vec3 intan;
out vec4 expos;
out vec2 extex;
out vec3 exnor;
out vec3 extan;
uniform mat4 MVPmat;
void main()
{
    expos = MVPmat * vec4(inpos, 1);
    extex = intex; 
    gl_Position = expos; 
}