#version 330
layout (location=0) in vec3 inpos;
layout (location=1) in vec2 intex;
out vec2 extex;
out vec4 expos;
uniform mat4 MVPmat;
void main()
{
	expos = MVPmat * vec4(inpos, 1);
    gl_Position = expos;
    extex = intex; 
}