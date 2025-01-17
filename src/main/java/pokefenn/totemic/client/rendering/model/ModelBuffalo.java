// Date: 15.09.2016 15:57:08
// Template version 1.1
// Java generated by Techne
package pokefenn.totemic.client.rendering.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelBuffalo extends ModelBase
{
    private final ModelRenderer head;
    private final ModelRenderer body;
    private final ModelRenderer back;
    private final ModelRenderer udder;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;
    private final ModelRenderer leg4;
    private final ModelRenderer hoof1;
    private final ModelRenderer hoof2;
    private final ModelRenderer hoof3;
    private final ModelRenderer hoof4;
    private final ModelRenderer tail;
    private final ModelRenderer tailhairs;
    private final ModelRenderer hornbase1;
    private final ModelRenderer hornbase2;
    private final ModelRenderer horn1;
    private final ModelRenderer horn2;
    private final ModelRenderer horn3;
    private final ModelRenderer horn4;
    private final ModelRenderer horn5;
    private final ModelRenderer horn6;

    public ModelBuffalo()
    {
        textureWidth = 64;
        textureHeight = 64;

        head = new ModelRenderer(this, 0, 45);
        head.addBox(-4.5F, -7F, -7F, 9, 7, 9);
        head.setRotationPoint(0F, 6F, -7F);
        head.setTextureSize(64, 64);
        head.mirror = true;
        setRotation(head, 1.570796F, 0F, 0F);
        body = new ModelRenderer(this, 0, 0);
        body.addBox(-6F, -10F, -9F, 12, 10, 13);
        body.setRotationPoint(0F, 7F, 2F);
        body.setTextureSize(64, 64);
        body.mirror = true;
        setRotation(body, 1.48353F, 0F, 0F);
        back = new ModelRenderer(this, 0, 23);
        back.addBox(-5.5F, 0F, -8.5F, 11, 10, 12);
        back.setRotationPoint(0F, 7F, 2F);
        back.setTextureSize(64, 64);
        back.mirror = true;
        setRotation(back, 1.48353F, 0F, 0F);
        udder = new ModelRenderer(this, 28, 46);
        udder.addBox(-3.5F, 4F, -9.5F, 7, 5, 1);
        udder.setRotationPoint(0F, 7F, 2F);
        udder.setTextureSize(64, 64);
        udder.mirror = true;
        setRotation(udder, 1.48353F, 0F, 0F);
        leg1 = new ModelRenderer(this, 46, 18);
        leg1.addBox(-1F, 0F, -3F, 4, 11, 5);
        leg1.setRotationPoint(4F, 10F, -5F);
        leg1.setTextureSize(64, 64);
        leg1.mirror = true;
        setRotation(leg1, 0.1396263F, 0F, 0F);
        leg1.mirror = false;
        leg2 = new ModelRenderer(this, 46, 18);
        leg2.addBox(-3F, 0F, -3F, 4, 11, 5);
        leg2.setRotationPoint(-4F, 10F, -5F);
        leg2.setTextureSize(64, 64);
        leg2.mirror = true;
        setRotation(leg2, 0.1396263F, 0F, 0F);
        leg3 = new ModelRenderer(this, 48, 43);
        leg3.addBox(-1F, 0F, -3F, 4, 8, 4);
        leg3.setRotationPoint(4F, 10F, 10F);
        leg3.setTextureSize(64, 64);
        leg3.mirror = true;
        setRotation(leg3, 0F, 0F, 0F);
        leg3.mirror = false;
        leg4 = new ModelRenderer(this, 48, 43);
        leg4.addBox(-3F, 0F, -3F, 4, 8, 4);
        leg4.setRotationPoint(-4F, 10F, 10F);
        leg4.setTextureSize(64, 64);
        leg4.mirror = true;
        setRotation(leg4, 0F, 0F, 0F);
        hoof1 = new ModelRenderer(this, 46, 34);
        hoof1.addBox(-0.5F, 8F, 0F, 3, 6, 3);
        hoof1.setRotationPoint(4F, 10F, -5F);
        hoof1.setTextureSize(64, 64);
        hoof1.mirror = true;
        setRotation(hoof1, 0F, 0F, 0F);
        hoof1.mirror = false;
        hoof2 = new ModelRenderer(this, 46, 34);
        hoof2.addBox(-2.5F, 8F, 0F, 3, 6, 3);
        hoof2.setRotationPoint(-4F, 10F, -5F);
        hoof2.setTextureSize(64, 64);
        hoof2.mirror = true;
        setRotation(hoof2, 0F, 0F, 0F);
        hoof3 = new ModelRenderer(this, 48, 55);
        hoof3.addBox(-0.5F, 8F, -2F, 3, 6, 3);
        hoof3.setRotationPoint(4F, 10F, 10F);
        hoof3.setTextureSize(64, 64);
        hoof3.mirror = true;
        setRotation(hoof3, 0F, 0F, 0F);
        hoof3.mirror = false;
        hoof4 = new ModelRenderer(this, 48, 55);
        hoof4.addBox(-2.5F, 8F, -2F, 3, 6, 3);
        hoof4.setRotationPoint(-4F, 10F, 10F);
        hoof4.setTextureSize(64, 64);
        hoof4.mirror = true;
        setRotation(hoof4, 0F, 0F, 0F);
        tail = new ModelRenderer(this, 28, 53);
        tail.addBox(-1F, 0F, -9.5F, 2, 1, 8);
        tail.setRotationPoint(0F, 7F, 12F);
        tail.setTextureSize(64, 64);
        tail.mirror = true;
        setRotation(tail, 1.48353F, 0F, 0F);
        tailhairs = new ModelRenderer(this, 35, 62);
        tailhairs.addBox(-1F, 0F, -10.5F, 2, 1, 1);
        tailhairs.setRotationPoint(0F, 7F, 12F);
        tailhairs.setTextureSize(64, 64);
        tailhairs.mirror = true;
        setRotation(tailhairs, 1.48353F, 0F, 0F);
        hornbase1 = new ModelRenderer(this, 52, 0);
        hornbase1.addBox(-7.5F, -4F, -1F, 4, 2, 2);
        hornbase1.setRotationPoint(0F, 6F, -7F);
        hornbase1.setTextureSize(64, 64);
        hornbase1.mirror = true;
        setRotation(hornbase1, 1.570796F, 0F, 0F);
        hornbase2 = new ModelRenderer(this, 52, 0);
        hornbase2.addBox(3.5F, -4F, -1F, 4, 2, 2);
        hornbase2.setRotationPoint(0F, 6F, -7F);
        hornbase2.setTextureSize(64, 64);
        hornbase2.mirror = true;
        setRotation(hornbase2, 1.570796F, 0F, 0F);
        hornbase2.mirror = false;
        horn1 = new ModelRenderer(this, 52, 4);
        horn1.addBox(6.5F, -4F, 0F, 2, 2, 4);
        horn1.setRotationPoint(0F, 6F, -7F);
        horn1.setTextureSize(64, 64);
        horn1.mirror = true;
        setRotation(horn1, 1.570796F, 0F, 0F);
        horn1.mirror = false;
        horn2 = new ModelRenderer(this, 52, 10);
        horn2.addBox(5.5F, -4F, 2F, 2, 2, 3);
        horn2.setRotationPoint(0F, 6F, -7F);
        horn2.setTextureSize(64, 64);
        horn2.mirror = true;
        setRotation(horn2, 1.570796F, 0F, 0F);
        horn2.mirror = false;
        horn3 = new ModelRenderer(this, 52, 15);
        horn3.addBox(5.5F, -4F, 5F, 1, 1, 1);
        horn3.setRotationPoint(0F, 6F, -7F);
        horn3.setTextureSize(64, 64);
        horn3.mirror = true;
        setRotation(horn3, 1.570796F, 0F, 0F);
        horn3.mirror = false;
        horn4 = new ModelRenderer(this, 52, 4);
        horn4.addBox(-8.5F, -4F, 0F, 2, 2, 4);
        horn4.setRotationPoint(0F, 6F, -7F);
        horn4.setTextureSize(64, 64);
        horn4.mirror = true;
        setRotation(horn4, 1.570796F, 0F, 0F);
        horn5 = new ModelRenderer(this, 52, 10);
        horn5.addBox(-7.5F, -4F, 2F, 2, 2, 3);
        horn5.setRotationPoint(0F, 6F, -7F);
        horn5.setTextureSize(64, 64);
        horn5.mirror = true;
        setRotation(horn5, 1.570796F, 0F, 0F);
        horn6 = new ModelRenderer(this, 52, 15);
        horn6.addBox(-6.5F, -4F, 5F, 1, 1, 1);
        horn6.setRotationPoint(0F, 6F, -7F);
        horn6.setTextureSize(64, 64);
        horn6.mirror = true;
        setRotation(horn6, 1.570796F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, -0.75F, 0);
        GlStateManager.scale(1.5F, 1.5F, 1.5F);
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);

        if (isChild)
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 6.0F * scale, 4.0F * scale);
        }

        head.render(scale);
        hornbase1.render(scale);
        hornbase2.render(scale);
        horn1.render(scale);
        horn2.render(scale);
        horn3.render(scale);
        horn4.render(scale);
        horn5.render(scale);
        horn6.render(scale);

        if (isChild)
        {
            float childScale = 0.5F;
            GlStateManager.popMatrix();
            GlStateManager.scale(childScale, childScale, childScale);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
        }

        tailhairs.render(scale);
        hoof1.render(scale);
        leg1.render(scale);
        leg3.render(scale);
        hoof3.render(scale);
        leg2.render(scale);
        hoof2.render(scale);
        leg4.render(scale);
        hoof4.render(scale);
        back.render(scale);
        udder.render(scale);
        tail.render(scale);
        body.render(scale);

        GlStateManager.popMatrix();
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, Entity entity)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);

        head.rotateAngleX = headPitch / (180F / (float) Math.PI) + ((float) Math.PI / 2F);
        hornbase1.rotateAngleX = hornbase2.rotateAngleX = horn1.rotateAngleX = horn2.rotateAngleX = horn3.rotateAngleX
            = horn4.rotateAngleX = horn5.rotateAngleX = horn6.rotateAngleX = head.rotateAngleX;

        head.rotateAngleY = hornbase1.rotateAngleY = hornbase2.rotateAngleY = horn1.rotateAngleY = horn2.rotateAngleY = horn3.rotateAngleY
            = horn4.rotateAngleY = horn5.rotateAngleY = horn6.rotateAngleY = netHeadYaw / (180F / (float) Math.PI);

        hoof1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        leg1.rotateAngleX = hoof1.rotateAngleX + ((float) Math.PI * 8F / 180F);
        hoof2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        leg2.rotateAngleX = hoof2.rotateAngleX + ((float) Math.PI * 8F / 180F);

        leg3.rotateAngleX = hoof3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        leg4.rotateAngleX = hoof4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
