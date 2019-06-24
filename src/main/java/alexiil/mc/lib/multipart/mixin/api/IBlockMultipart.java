/*
 * Copyright (c) 2019 AlexIIL
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package alexiil.mc.lib.multipart.mixin.api;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

/** Interface for blocks to implement if parts of them can be targeted and broken independently from other parts. */
public interface IBlockMultipart<T> {
    Class<T> getKeyClass();

    /** Multipart version of {@link Block#onBlockBreakStart(BlockState, World, BlockPos, PlayerEntity)} */
    void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player, T subpart);

    /** Multipart version of {@link Block#calcBlockBreakingDelta(BlockState, PlayerEntity, BlockView, BlockPos)} */
    float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView view, BlockPos pos, T subpart);

    /** Multipart version of {@link Block#onBreak(World, BlockPos, BlockState, PlayerEntity)} */
    void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, T subpart);

    /** Called instead of {@link World#clearBlockState(BlockPos, boolean)} in
     * {@link ServerPlayerInteractionManager#destroyBlock} */
    boolean clearBlockState(World world, BlockPos pos, T subpart);

    /** Multipart version of {@link Block#onBroken(IWorld, BlockPos, BlockState)} */
    void onBroken(IWorld world, BlockPos pos, BlockState state, T subpart);

    /** Multipart version of
     * {@link Block#afterBreak(World, PlayerEntity, BlockPos, BlockState, BlockEntity, ItemStack)}. */
    void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity be,
        ItemStack stack, T subpart);

    VoxelShape getPartShape(BlockState state, World world, BlockPos pos, Vec3d hitVec);

    @Nullable
    T getTargetedMultipart(BlockState state, World world, BlockPos pos, Vec3d hitVec);
}
