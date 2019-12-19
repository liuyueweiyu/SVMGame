#version 330
layout (location=0) in vec3 inpos;
layout (location=1) in vec2 intex;
layout (location=2) in vec3 innor;
layout (location=3) in vec3 intan;
out vec4 expos;
out vec2 extex;
out vec3 exnor;
out vec3 extan;
out vec3 exsun;
uniform vec3 sun;
uniform mat4 MVPmat;
uniform mat4 VPmat;
void main()
{
    expos = MVPmat * vec4(inpos, 1);
    extex = intex; 
    exnor = (MVPmat * vec4(innor, 0)).xyz;
    extan = (MVPmat * vec4(intan, 0)).xyz;
    exsun = (VPmat * vec4(sun,1)).xyz;
    gl_Position = expos; 
}