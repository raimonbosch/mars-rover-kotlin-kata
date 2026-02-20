# Mars Rover kata

### Functional requirements
```
Given:
 - a two dimensional map of Mars
 - the initial starting point and direction of the Rover
 
When:
 - a command is received
   move `forward` or `backward` or rotate `left` or `right` (90 degrees)

Then:
 - move the Rover
   if the Rover disappears over the edge of the map (the horizon), continue on the other side (remember, Mars is a sphere)
```

#### Bonus point

After ensuring that the functional requirements have been met, as a bonus point (not necessary but more than welcome), add a new feature:
```
Given:
 - a list of obstacles with their exact location
 
When:
 - Rover moves

And:
 - Rover encounters an obstacle

Then:
 - report back the obstacle. The Rover should stay in its previous position
```

### Must (These points are mandatory)

- Fulfill the [Functional Requirements](#functional-requirements) stated in this readme.
- Refactor the provided code, creating new classes, methods or whatever needed.
- Be testable. This means that we should not need to run the main app in order to check that everything is working.
- Be self compiled.
- Be self executable.

### Should (Nice to have)

- Fulfill the [Bonus point](#bonus-point) section of this readme.
- Be bug free.
- Use any design patterns you know and feel that help solve this problem.
- Be extensible to allow the introduction of new features in an easy way.
- Use any package dependency mechanism.

## Solution

![Mars Rovers Challenge - Mars Rovers Challenge (1)](https://github.com/user-attachments/assets/7796c660-6656-46c7-9a84-17d8d8351cc4)

The solution is based on two use cases. The `PlanetMapGenerationUseCase` will be responsible of generating the data structure to navigate through the mars map, while the `MarsRoverRouteUseCase` will execute the movements of the car on the map and report back to our interface.

- **Step 1 - Generate map.** The planet generation uses a `Mesh` data structure with a collection of `Node` that are connected by evaluating if they are close to each other by `Position(x,y)` or if they are the end of the beginning of the Mesh edge to do the circular connections. Check implementation at `MeshBuilder` and `MeshBuilder.are(Horizontally|Vertically)*Connected` methods.
  Note that to not generate circular dependencies bewteen nodes, the neigborhs of a Node are saved as a list of neighboring `Positions`. This way the real node with all information must be obtained from the `Mesh` by doing `Mesh.retrieve(Position(x, y))` once you have obtained the neighbor position in coordinates. It's a little more complex, but it is less prone to errors.
- **Step 2 - Move position on PlanetMapRepository.** On the other side, the `MarsRoverRouteUseCase` handles the coordination between the movement of the car and the map topology. The car is based on the `AlienCar` interface and its implementation `MarsRoverCar`. Here you handle the rotation operations and the forward/backward movement. According to this position we move around the `PlanetMapRepository` that we have built previously via the `PlanetMapGenerationUseCase`. Here we realize the movement, we check the neighbor south, west, east or nord of a given `Node` and return the next position.
- **Step 3 - Check returned Node by PlanetMapRepository.move.** If the returned next `Node` of the repository is an `Obstacle` the car won't move, if it's a regular Node it will move.
- **Step 4 - Move the alien car (or not).** Depending if the Node is an Obstacle or not we move the car and update its `Position`.
- **Step 5 - Return car position the CLI interface.** With the given results we print a map in the shell UI. The UI is more or less adapted the same basic UI we already had in Java where you could type parameters such as height, widhth, etc... I added the number of random obstacles and a tiny print of the map to have a slightly better idea where we are at.

## To run the project

> ./gradlew build
>
> ./gradlew run --console=plain

Or you can use directly the command line

> ./bin/mars-rover

You will be prompted to the CLI shell interface.

