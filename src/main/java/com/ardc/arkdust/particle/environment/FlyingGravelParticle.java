package com.ardc.arkdust.particle.environment;

import com.ardc.arkdust.registry.ParticleRegistry;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Vector3d;

import java.awt.*;
import java.util.Locale;
import java.util.Random;

public class FlyingGravelParticle extends TextureSheetParticle {
    private static final Vector3d defSpeed = new Vector3d(0.4,-0.12,0.3);
    private static final Color defColor = new Color(93, 93, 93, 1);

    private void shouldRemoveSelf(){
        if(y>80 || y<56){
            remove();
        }
    }

    @Override
    public void render(VertexConsumer p_225606_1_, Camera p_225606_2_, float p_225606_3_) {
        if(removed) return;
        super.render(p_225606_1_, p_225606_2_, p_225606_3_);
    }

    private void randomSpeed(Vector3d basicSpeed, ClientLevel world, Color color){
        float factor = 1 + (world.isRaining() ? 0.4F : 0) + (world.isThundering() ? 0.6F : 0);
        Random r = new Random();
        xd = (basicSpeed.x + r.nextFloat() * 0.3f - 0.1f )*factor;
        yd = basicSpeed.y + r.nextFloat() * 0.12f;
        xd = (basicSpeed.x + r.nextFloat() * 0.3f - 0.08f )*factor;
        int colorOffset = random.nextInt(8)-4;
        setColor(color.getRed()+colorOffset,color.getGreen()+colorOffset,color.getBlue()+colorOffset);
        setAlpha(color.getAlpha());
    }
    protected FlyingGravelParticle(ClientLevel world, double x, double y, double z, Vector3d speed, Color extraColor, float diameter) {
        super(world, x, y, z, speed.x,speed.y,speed.z);
        shouldRemoveSelf();
        this.lifetime = 70;
        randomSpeed(speed,world,extraColor);
        scale(diameter);
        this.hasPhysics = true;
    }

    protected FlyingGravelParticle(ClientLevel world, double x, double y, double z) {
        super(world, x, y, z);
        shouldRemoveSelf();
        this.lifetime = 70;
        randomSpeed(defSpeed,world,defColor);
        scale(1.2F);
        this.hasPhysics = true;

    }

    @Override
    public void tick() {
        if(y < 56 || y > 80){
            this.remove();
            return;
        }
        super.tick();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class Type extends ParticleType<Type> implements ParticleOptions {
            private final Vector3d speed;
            private final Color color;
            private final float diameter;
            public static final ParticleOptions.Deserializer<FlyingGravelParticle.Type> DESERIALIZER = new ParticleOptions.Deserializer<FlyingGravelParticle.Type>() {

                @Override
                public FlyingGravelParticle.Type fromCommand(ParticleType<FlyingGravelParticle.Type> particleTypeIn, StringReader reader) throws CommandSyntaxException {
                    if(!reader.canRead()){
                        return new FlyingGravelParticle.Type();
                    }
                    final int MIN_COLOUR = 0;
                    final int MAX_COLOUR = 255;
                    reader.expect(' ');
                    double speedX = reader.readDouble();
                    reader.expect(' ');
                    double speedY = reader.readDouble();
                    reader.expect(' ');
                    double speedZ = reader.readDouble();
                    reader.expect(' ');
                    int red = clamp(reader.readInt(), MIN_COLOUR, MAX_COLOUR);
                    reader.expect(' ');
                    int green = clamp(reader.readInt(), MIN_COLOUR, MAX_COLOUR);
                    reader.expect(' ');
                    int blue = clamp(reader.readInt(), MIN_COLOUR, MAX_COLOUR);
                    reader.expect(' ');
                    int alpha = clamp(reader.readInt(), 1, MAX_COLOUR);
                    reader.expect(' ');
                    float diameter = reader.readFloat();
                    return new FlyingGravelParticle.Type(new Vector3d(speedX, speedY, speedZ), new Color(red, green, blue, alpha), diameter);
                }

                @Override
                public Type fromNetwork(ParticleType<Type> type, FriendlyByteBuf buffer) {
                    final int MIN_COLOUR = 0;
                    final int MAX_COLOUR = 255;
                    double speedX = buffer.readDouble();
                    double speedY = buffer.readDouble();
                    double speedZ = buffer.readDouble();
                    int red = clamp(buffer.readInt(), MIN_COLOUR, MAX_COLOUR);
                    int green = clamp(buffer.readInt(), MIN_COLOUR, MAX_COLOUR);
                    int blue = clamp(buffer.readInt(), MIN_COLOUR, MAX_COLOUR);
                    int alpha = clamp(buffer.readInt(), 1, MAX_COLOUR);
                    float diameter = buffer.readFloat();
                    return new FlyingGravelParticle.Type(new Vector3d(speedX, speedY, speedZ), new Color(red, green, blue, alpha), diameter);
                }

                public static int clamp(int num,int min,int max){
                    if(min<=max) throw new IllegalArgumentException("max number" + max + " should better than min number" + max);
                    return Math.max(min,Math.min(max,num));
                }
        };

        public Type(Vector3d speed, Color color, float diameter) {
            super(false, Type.DESERIALIZER);
            this.speed = speed;
            this.color = color;
            this.diameter = diameter;
        }

        @Override
        public ParticleType<?> getType() {
            return ParticleRegistry.FLYING_GRAVEL.get();
        }


        @Override
        public void writeToNetwork(FriendlyByteBuf buffer) {
            buffer.writeDouble(this.speed.x);
            buffer.writeDouble(this.speed.y);
            buffer.writeDouble(this.speed.z);
            buffer.writeInt(this.color.getRed());
            buffer.writeInt(this.color.getGreen());
            buffer.writeInt(this.color.getBlue());
            buffer.writeInt(this.color.getAlpha());
            buffer.writeFloat(this.diameter);
        }

        @Override
        public String writeToString() {
            return String.format(Locale.ROOT, "%s %.2f %i %i %i %i %.2d %.2d %.2d",
                    ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()), diameter, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), speed.x, speed.y, speed.z);
        }

        public Vector3d getSpeed() {
            return speed;
        }

        public Color getColor() {
            return color;
        }

        public float getDiameter() {
            return diameter;
        }

        public Type() {
            super(false, Type.DESERIALIZER);
            speed = defSpeed;
            color = defColor;
            diameter = 1.2F;
        }

        @Override
        public Codec<Type> codec() {
            return Codec.unit(new Type(this.speed, this.color, this.diameter));
        }
    }

    public record Factory(SpriteSet sprite) implements ParticleProvider<Type> {
        @Override
        public Particle createParticle(Type type, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            FlyingGravelParticle particle = new FlyingGravelParticle(world, x, y, z, new Vector3d(type.speed.x, type.speed.y, type.speed.z), type.color, type.diameter);
            particle.setSpriteFromAge(sprite);
            return particle;
        }
    }
}
