#version 330

in vec4 expos;
out int fragColor;
uniform int label;

float depth(in float z)
{
	return z>=0?1-1/(z+1):1;
}

void main(void){

	fragColor = label;
	gl_FragDepth  = depth(expos.z);
}