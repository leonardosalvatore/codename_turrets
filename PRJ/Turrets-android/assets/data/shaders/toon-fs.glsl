uniform vec3 lightDir;
varying vec3 normal;

void main()
{
	float intensity;
	vec4 color;
	vec4 base_color = vec4(1.0,0,0,1.0);
	
	intensity = dot(normalize(lightDir),normal);

	if (intensity > 0.95)
		color = vec4(0.8,0.8,0.8,1.0);
	else if (intensity > 0.5)
		color = vec4(0.6,0.6,0.6,1.0);
	else if (intensity > 0.25)
		color = vec4(0.4,0.4,0.4,1.0);
	else
		color = vec4(0.2,0.2,0.2,1.0);

	gl_FragColor = color;

}