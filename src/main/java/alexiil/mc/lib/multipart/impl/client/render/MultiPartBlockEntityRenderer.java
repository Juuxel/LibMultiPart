/*
 * Copyright (c) 2019 AlexIIL
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package alexiil.mc.lib.multipart.impl.client.render;

import net.minecraft.client.render.block.entity.BlockEntityRenderer;

import alexiil.mc.lib.multipart.api.AbstractPart;
import alexiil.mc.lib.multipart.api.render.MultiPartRenderRegistry;
import alexiil.mc.lib.multipart.api.render.PartRenderer;
import alexiil.mc.lib.multipart.impl.MultiPartBlockEntity;
import alexiil.mc.lib.multipart.impl.PartHolder;

public class MultiPartBlockEntityRenderer extends BlockEntityRenderer<MultiPartBlockEntity> {
    @Override
    public void render(MultiPartBlockEntity be, double x, double y, double z, float partialTicks, int breakProgress) {
        for (PartHolder holder : be.getContainer().parts) {
            AbstractPart part = holder.part;
            renderPart(part, part.getClass(), partialTicks, breakProgress);
        }
    }

    static <P extends AbstractPart> void renderPart(AbstractPart part, Class<P> clazz, float partialTicks,
        int breakProgress) {
        PartRenderer<? super P> renderer = MultiPartRenderRegistry.getRenderer(clazz);
        if (renderer != null) {
            renderer.render(clazz.cast(part), partialTicks, breakProgress);
        }
    }
}
