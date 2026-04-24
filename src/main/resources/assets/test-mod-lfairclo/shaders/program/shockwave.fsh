#version 150

uniform sampler2D DiffuseSampler;
uniform vec3 Origin;
uniform float Radius;
uniform float Thickness;
uniform float Time;

in vec2 texCoord;
out vec4 fragColor;

// You’ll need depth + inverse matrices for proper world position
uniform sampler2D DepthSampler;
uniform mat4 InvProjection;
uniform mat4 InvView;

vec3 getWorldPos(vec2 uv) {
    float depth = texture(DepthSampler, uv).r;

    vec4 clip = vec4(uv * 2.0 - 1.0, depth, 1.0);
    vec4 view = InvProjection * clip;
    view /= view.w;

    vec4 world = InvView * view;
    return world.xyz;
}

void main() {
    vec4 base = texture(DiffuseSampler, texCoord);

    vec3 worldPos = getWorldPos(texCoord);

    float dist = distance(worldPos.xz, Origin.xz);

    // thin expanding ring
    float band =
        smoothstep(Radius - Thickness, Radius, dist) -
        smoothstep(Radius, Radius + Thickness, dist);

    // optional falloff
//    float fade = exp(-0.02 * Radius);

    vec3 color = vec3(1.0, 0.5, 0.1); // orange glow

    base.rgb += band * color * fade;

    fragColor = base;
}