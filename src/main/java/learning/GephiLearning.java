package learning;

import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.io.generator.plugin.DynamicGraph;
import org.gephi.io.generator.plugin.RandomGraph;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.AppendProcessor;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;

public class GephiLearning {
    public void script() {
        //Init a project - and therefore a workspace
        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        pc.newProject();
        Workspace workspace = pc.getCurrentWorkspace();

        //Generate a new random graph into a container
        Container container = Lookup.getDefault().lookup(Container.Factory.class).newContainer();
        RandomGraph randomGraph = new RandomGraph();
        randomGraph.setNumberOfNodes(500);
        randomGraph.setWiringProbability(0.005);
        randomGraph.generate(container.getLoader());

        //Append container to graph structure
        ImportController importController = Lookup.getDefault().lookup(ImportController.class);
        importController.process(container, new DefaultProcessor(), workspace);

        //Generate another graph and append it to the current workspace
        Container container2 = Lookup.getDefault().lookup(Container.Factory.class).newContainer();
        RandomGraph randomGraph2 = new RandomGraph();
        randomGraph2.setNumberOfNodes(100);
        randomGraph2.setWiringProbability(0.01);
        randomGraph2.generate(container.getLoader());
        importController.process(container2, new AppendProcessor(), workspace);     //Use AppendProcessor to append to current workspace

        //Create new workspace
        Workspace workspace2 = pc.newWorkspace(pc.getCurrentProject());
        pc.openWorkspace(workspace2);       //Set as current workspace

        //Generate dynamic graph into workspace 2
        Container container3 = Lookup.getDefault().lookup(Container.Factory.class).newContainer();

        DynamicGraph dynamicGraph = new DynamicGraph();
        dynamicGraph.generate(container3.getLoader());
        importController.process(container3, new DefaultProcessor(), workspace2);
    }
}
