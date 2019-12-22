#version 330 core
out vec4 color;
in vec2 pos;
in float t;
uniform sampler2D tex;

void main()
{

    float r=0;
    float g=0;
    float b=0;
    if(t<0.05){
    r=t*0.541;
    g=t*1.01;
    b=t*9;
    }
    else if(t<0.35){
    r=0.02745+(t-0.1)*0.07843;
    g=0.05098+(t-0.1)*1.856;
    b=0.4549+(t-0.1)*0.7189;
    }
    else if(t<0.65){
    r=0.050980+(t-0.5)*2.4836;
    g=0.607843+(t-0.5)*1.04575;
    b=0.65882+(t-0.5)*0.8496;
    }
    else if(t>=0.65){
    r=0.784313+(t-0.8)*1.15686;
    g=0.941176+(t-0.8)*0.3333333;
    b=0.941176+(t-0.8)*0.4117;
    }
    color = texture(tex,pos);
    color = vec4(1-r,1-g,1-b,color.a);
    gl_FragDepth = 0;
}