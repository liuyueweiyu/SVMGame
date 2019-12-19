#version 330
in vec2 extex;
in vec4 expos;
in vec3 exnor;
in vec3 extan;
out vec4 fragColor;
uniform sampler2D diffuse_tex;
uniform sampler2D normals_tex;
uniform sampler2D specular_tex;
in vec3 exsun;
float depth(in float z)
{
	return z>0?1-1/(z+1):1;
}
void main()
{
    fragColor = texture(diffuse_tex, extex);
    fragColor = fragColor+texture(normals_tex, extex)-texture(normals_tex, extex);
    fragColor = fragColor+texture(specular_tex, extex)-texture(specular_tex, extex);
    vec3 dirlight = normalize(-exsun+expos.xyz);
    fragColor = vec4(0.2*fragColor.xyz+0.8*fragColor.xyz*(max(dot(-dirlight,exnor),0.0)),1);
    gl_FragDepth  = depth(expos.z);
}