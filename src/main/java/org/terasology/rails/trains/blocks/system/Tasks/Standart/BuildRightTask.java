/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.rails.trains.blocks.system.Tasks.Standart;

import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.rails.trains.blocks.components.TrainRailComponent;
import org.terasology.rails.trains.blocks.system.Builder.Command;
import org.terasology.rails.trains.blocks.system.Builder.CommandHandler;
import org.terasology.rails.trains.blocks.system.Builder.TaskResult;
import org.terasology.rails.trains.blocks.system.Config;
import org.terasology.rails.trains.blocks.system.Misc.Orientation;
import org.terasology.rails.trains.blocks.system.RailsSystem;
import org.terasology.rails.trains.blocks.system.Railway;
import org.terasology.rails.trains.blocks.system.Tasks.Task;

import javax.vecmath.Vector3f;
import java.util.ArrayList;

/**
 * Created by adeon on 10.09.14.
 */
public class BuildRightTask implements Task {
    @Override
    public boolean run(EntityRef selectedTrack, Vector3f position, Orientation orientation, boolean reverse) {

        if (selectedTrack == null) {
            return  false;
        }

        TrainRailComponent trainRailComponent = selectedTrack.getComponent(TrainRailComponent.class);
        float count = 90/ RailsSystem.STANDARD_ANGLE_CHANGE;
        ArrayList<Command> commands = new ArrayList<>();
        String chunkKey = Railway.getInstance().createChunk(position);

        if (trainRailComponent.pitch > 0) {
            commands.add(new Command(true, TrainRailComponent.TrackType.DOWN, position, orientation, chunkKey, false, reverse));
        } else if(trainRailComponent.pitch < 0) {
            commands.add(new Command(true, TrainRailComponent.TrackType.UP, position, orientation, chunkKey, false, reverse));
        }

        for (int i = 0; i<count; i++) {
            commands.add(new Command(true, TrainRailComponent.TrackType.RIGHT, position, new Orientation(0,0,0), chunkKey, false, reverse));
        }

        TaskResult taskResult = CommandHandler.getInstance().run(commands, selectedTrack, reverse);
        return taskResult.success;
    }
}

