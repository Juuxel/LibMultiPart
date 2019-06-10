package alexiil.mc.lib.multipart.mixin.api;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BoundingBox;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public interface IBlockSimpleMultipart<T> extends IBlockMultipart<T> {

    Map<T, VoxelShape> getSubParts(IWorld world, BlockPos pos, BlockState state);

    @Override
    @Nullable
    default T getTargetedMultipart(BlockState state, World w, BlockPos pos, Vec3d hitVec) {
        Map<T, VoxelShape> map = getSubParts(w, pos, state);

        for (Entry<T, VoxelShape> entry : map.entrySet()) {
            VoxelShape shape = entry.getValue();
            for (BoundingBox box : shape.getBoundingBoxes()) {
                if (box.expand(0.01).contains(hitVec)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    @Override
    default VoxelShape getPartShape(BlockState state, World world, BlockPos pos, Vec3d hitVec) {
        T targetted = getTargetedMultipart(state, world, pos, hitVec);
        if (targetted == null) {
            return VoxelShapes.empty();
        }
        return getSubParts(world, pos, state).get(targetted);
    }
}
