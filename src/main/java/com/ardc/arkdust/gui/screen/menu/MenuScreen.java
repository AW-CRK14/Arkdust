package com.ardc.arkdust.gui.screen.menu;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.gui.ArdMainInfoScreen;
import com.ardc.arkdust.gui.widget.pre.MainMenuTagCardButton;
import com.ardc.arkdust.capability.health_system.HealthSystemCapability;
import com.ardc.arkdust.capability.rdi_auth.RDIAccountAuthCapability;
import com.ardc.arkdust.capability.rdi_depot.RDIDepotCapability;
import com.ardc.arkdust.registry.CapabilityRegistry;
import com.ibm.icu.impl.Pair;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MenuScreen extends ArdMainInfoScreen {
    protected MenuScreen(ITextComponent p_i51108_1_) {
        super(p_i51108_1_);
    }

    int rightBoxBaseX;
    int rightBoxAddX;
    int rightBoxBaseY;
    MainMenuTagCardButton healthCard;
    MainMenuTagCardButton resourceCard;
    
    public static final ResourceLocation II_COLOR = new ResourceLocation(Utils.MOD_ID,"textures/gui/info/material/ii_color.png");

    @Override
    protected void init(){
        super.init();
        createData();
        healthCard = new MainMenuTagCardButton(rightBoxBaseX,rightBoxBaseY,rightBoxAddX,94,"health",(button)->{},new ResourceLocation(Utils.MOD_ID,"textures/gui/info/pic/health.png"),null,1,
                (button,fx,fy,ax,ay,stack)->{
                    PlayerEntity entity = minecraft.player;
                    if(entity != null) {
                        TextureManager manager = this.minecraft.getTextureManager();
                        manager.bind(new ResourceLocation(Utils.MOD_ID,"textures/gui/info/material/health_card_add.png"));
                        blit(stack,fx-3,fy,0,0,(int)(ay*0.375F),ay,(int)(ay*0.375F),ay);
                        drawHealthLine(stack, fx + 30, fy + 3, ax - 35, 16, (int)entity.getHealth(), (int)entity.getMaxHealth(), 0xFF0000, 5);

                        entity.getCapability(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY).ifPresent((i)-> drawHealthLine(stack, fx + 30, fy + 47, ax - 35, 16,i.ORI$getPoint(), HealthSystemCapability.ORI$level2Point(i.ORI$getRLevel()), 0xA51412, 14));
                        entity.getCapability(CapabilityRegistry.RDI_ACCOUNT_AUTH_CAPABILITY).ifPresent((i)->{
                            drawHealthLine(stack,fx+30,fy+69,ax-35,16,i.getSanity(),i.getMaxSanity(),0x6CC9BA,9);
                            Pair<Integer,Integer> p = i.AExp$getFlowAndLevel();
                            drawHealthLine(stack,fx+30,fy+25,ax-35,16,p.first, RDIAccountAuthCapability.levelIncludeAExp(p.second),0xFFBF00,8);
                        });
                    }
                });
        resourceCard = new MainMenuTagCardButton(rightBoxBaseX,rightBoxBaseY+100,rightBoxAddX,40,"resource",(button)->{},new ResourceLocation(Utils.MOD_ID,"textures/gui/info/pic/resource.png"),null,0,
                (button,fx,fy,ax,ay,stack)->{
                    PlayerEntity entity = minecraft.player;
                    if(entity != null) {
                        entity.getCapability(CapabilityRegistry.RDI_DEPOT_CAPABILITY).ifPresent((i)->{
                            drawScaledText(stack,0.85F,new StringTextComponent(int2PatternString(i.getObject(RDIDepotCapability.DepotObject.ORUNDUM))),0xC62A37,fx + 0.2F * ax,fy + 0.7F * ay,false);
                            drawScaledText(stack,0.85F,new StringTextComponent(int2PatternString(i.getObject(RDIDepotCapability.DepotObject.LMB))),0x6AC1D7,fx + 0.2F * ax,fy + 0.15F * ay,false);
                            drawScaledText(stack,0.85F,new StringTextComponent(int2PatternString(i.getObject(RDIDepotCapability.DepotObject.ORIGINITE_PRIME))),0xFFF78C,fx + 0.7F * ax,fy + 0.15F * ay,false);
                            drawScaledText(stack,0.85F,new StringTextComponent(int2PatternString(i.getObject(RDIDepotCapability.DepotObject.ORIGINIUM_INGOT))),0x04DEB6,fx + 0.7F * ax,fy + 0.7F * ay,false);
                        });
                    }
                });
    }

    public void drawHealthLine(MatrixStack stack ,int fx,int fy,int ax,int ay,int in,int all,int color,int color_index){
        this.minecraft.getTextureManager().bind(II_COLOR);
        int color_part = Math.min(all != 0 ? ax*in/all : 0,ax);
        int height = 5;
        blit(stack,fx+color_part,fy+ay-height,0,20*height,ax-color_part,height,1,21*height);
        blit(stack,fx,fy+ay-height,0,color_index*height,color_part,height,1,21*height);
        drawScaledCenterText(stack,1.3F,new StringTextComponent(String.valueOf(in)),color,fx+0.3F*ax,fy+ay-height,false,true);
        drawScaledText(stack,0.8F,new StringTextComponent("/" + all),0xE2E2E2,fx+ax-font.width("/" + all)*0.8F,fy+ay-height-7.2F,false);
    }

    private static String int2PatternString(int num){
        if(num>600000){
            return (num/100000)/10F + "m";
        }else if(num>5000){
            return (num/100)/10F + "k";
        }
        return String.valueOf(num);
    }


//    public void drawHealthLine(MatrixStack stack ,int fx,int fy,int ax,int ay,int numIn,int numAll,int renderIn,int renderAll,int color,int color_index){
//        if(extraBackText != null){
//            drawScaledText(stack,1.6F,new StringTextComponent(extraBackText),extraTextColor,fx,fy,false);
//        }
//        this.minecraft.getTextureManager().bind(II_COLOR);
//        int color_part = Math.min(renderAll != 0 ? ax*renderIn/renderAll : 0,ax);
//        int height = 5;
//        blit(stack,fx+color_part,fy+ay-height,0,20*height,ax-color_part,height,1,21*height);
//        blit(stack,fx,fy+ay-height,0,color_index*height,color_part,height,1,21*height);
//        drawScaledCenterText(stack,1.3F,new StringTextComponent(String.valueOf(numIn)),color,fx+0.3F*ax,fy+ay-height,false,true);
//        drawScaledText(stack,0.8F,new StringTextComponent("/" + numAll),0xAEAEAE,fx+ax-font.width("/" + numAll),fy+ay-height-9,false);
//        this.font.draw(stack,new StringTextComponent("/" + numAll),fx+ax-font.width("/" + numAll),fy+ay-height-9,0xAEAEAE);
//    }

    public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks){
        super.render(ms,mouseX,mouseY,partialTicks);
        createData();
        TextureManager manager = this.minecraft.getTextureManager();

//        manager.bind(plank);
//        setAlpha(0.16F);
//        blit(ms,rightBoxBaseX,rightBoxBaseY,0,0,rightBoxAddX,40,90,90);
        
        manager.bind(getFromMaterial("bg_color"));
        defaultAlpha();
        blit(ms,rightBoxBaseX+6,rightBoxBaseY-12,0,0,rightBoxAddX - 12,158,1,1740);

        manager.bind(getFromMaterial("account_data"));
        blit(ms,rightBoxBaseX,rightBoxBaseY-18,0,0,48,12,48,12);

        healthCard.renderWithAlphaFac(ms,mouseX,mouseY,partialTicks,(float)timer/5);
        resourceCard.renderWithAlphaFac(ms,mouseX,mouseY,partialTicks,(float)timer/5);
    }

    private void createData(){
        rightBoxBaseX = (int) (this.width * 0.7F + 6);
        rightBoxAddX = (int) (this.width * 0.3F - 12);
        rightBoxBaseY = this.height/8 + 24;
    }



    public static class OpenMenuGui {
        public OpenMenuGui(){
            Minecraft.getInstance().setScreen(new MenuScreen(new TranslationTextComponent(Utils.MOD_ID + ".gui.infomac.menu")));
//            Minecraft.getInstance().pushGuiLayer(new ArdMainInfoScreen(new TranslationTextComponent(Utils.MOD_ID + ".gui.infomac.menu")));
        }
    }


}
