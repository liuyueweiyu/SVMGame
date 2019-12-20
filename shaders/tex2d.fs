#version 330
in vec4 expos;
in vec2 extex;
out vec4 fragColor;
uniform sampler2D tex;

void main(void){
	fragColor = texture(tex,extex);
	gl_FragDepth = texture(tex,extex).a !=0? expos.z:1;
}