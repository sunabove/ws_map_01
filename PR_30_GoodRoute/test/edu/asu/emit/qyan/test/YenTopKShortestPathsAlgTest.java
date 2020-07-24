/*
 *
 * Copyright (c) 2004-2008 Arizona State University.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY ARIZONA STATE UNIVERSITY ``AS IS'' AND
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL ARIZONA STATE UNIVERSITY
 * NOR ITS EMPLOYEES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package edu.asu.emit.qyan.test;

import java.util.List;

import org.junit.Test;

import edu.asu.emit.qyan.alg.control.DijkstraShortestPathAlg;
import edu.asu.emit.qyan.alg.control.YenTopKShortestPathsAlg;
import edu.asu.emit.qyan.alg.model.Graph;
import edu.asu.emit.qyan.alg.model.Path;
import edu.asu.emit.qyan.alg.model.VariableGraph;

/**
 * @author <a href='mailto:Yan.Qi@asu.edu'>Yan Qi</a>
 * @version $Revision: 784 $
 * @latest $Id: YenTopKShortestPathsAlgTest.java 784 2009-06-19 20:08:40Z qyan $
 */
public class YenTopKShortestPathsAlgTest
{
	// The graph should be initiated only once to guarantee the correspondence 
	// between vertex id and node id in input text file. 
	static Graph graph = new VariableGraph("data/test_7");
	
	@Test
	public void testDijkstraShortestPathAlg()
	{
		System.out.println("Testing Dijkstra Shortest Path Algorithm!");
		//VariableGraph graph = new VariableGraph("data/network");
		DijkstraShortestPathAlg alg = new DijkstraShortestPathAlg(graph);
		//System.out.println(alg.get_shortest_path(graph.get_vertex(0), graph.get_vertex(20)));
		System.out.println(alg.get_shortest_path(graph.get_vertex(0), graph.get_vertex(2)));
	}
	
	@Test
	public void testYenShortestPathsAlg()
	{		
		System.out.println("Testing batch processing of top-k shortest paths!");
		YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);
		List<Path> shortest_paths_list = yenAlg.get_shortest_paths(
				//graph.get_vertex(1), graph.get_vertex(3), 300);
				graph.get_vertex(0), graph.get_vertex(2), 100);
		System.out.println(":"+shortest_paths_list);
		System.out.println(yenAlg.get_result_list().size());	
	}
	
	@Test
	public void testYenShortestPathsAlg2()
	{
		System.out.println("Obtain all paths in increasing order!");
		YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(
				graph, graph.get_vertex(0), graph.get_vertex(2));
		int i=0;
		while(yenAlg.has_next())
		{
			System.out.println("Path "+i+" : "+yenAlg.next());
		}
	}
}
