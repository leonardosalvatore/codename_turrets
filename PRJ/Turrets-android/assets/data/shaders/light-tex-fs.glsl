#ifdef GL_ES
#define LOWP lowp
#define MEDP mediump
#define HIGP highp
precision lowp float;
#else
#define LOWP
#define MEDP
#define HIGP
#endif


uniform vec3 u_lightColor;
varying LOWP float intensity;

void main() {
	vec4 color = vec4(1.0,0,0,1.0);
	vec4 baseColor = vec4(u_lightColor.r,u_lightColor.g,u_lightColor.b,1.0);
	
	if (intensity > 0.95)
		color = vec4(0.9,0.9,0.9,1.0);
	else if (intensity > 0.8)
		color = vec4(0.8,0.8,0.8,1.0);
	else if (intensity > 0.65)
		color = vec4(0.6,0.6,0.6,1.0);
	else if (intensity > 0.5)
		color = vec4(0.5,0.5,0.5,1.0);
	else if (intensity > 0.25)
		color = vec4(0.3,0.3,0.3,1.0);
	else
		color = vec4(0.2,0.2,0.2,1.0);
	
	
	gl_FragColor = baseColor*color*intensity;
	}


