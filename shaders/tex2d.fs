#version 330
in vec4 expos;
in vec2 extex;
out vec4 fragColor;
uniform sampler2D tex;

float depth(in float z)
{
	return z>=0?1-1/(z+1):1;
}

void main(void){
	fragColor = texture(tex,extex);
	gl_FragDepth  = depth(expos.z);
}