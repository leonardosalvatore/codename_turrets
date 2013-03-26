uniform mat4 u_projView;
varying vec3 normal;

attribute vec4 a_position;

void main()
{
	
	gl_Position = u_projView * a_position;
	normal = normalize(gl_NormalMatrix * gl_Normal);
 }