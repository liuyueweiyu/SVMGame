#version 330
in vec2 extex;
in vec4 expos;
out vec4 fragColor;
uniform sampler2D diffuse_tex;
in vec3 sun;
float depth(in float z)
{
	return z>0?1-1/(z+1):1;
}
void main()
{
    fragColor = 1.3*texture(diffuse_tex, extex);

    gl_FragDepth  = depth(expos.z);
}