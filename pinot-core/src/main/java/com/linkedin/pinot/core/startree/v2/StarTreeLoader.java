/**
 * Copyright (C) 2014-2018 LinkedIn Corp. (pinot-core@linkedin.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.linkedin.pinot.core.startree.v2;

import com.linkedin.pinot.core.indexsegment.immutable.ImmutableSegment;
import com.linkedin.pinot.core.segment.index.SegmentMetadataImpl;
import com.linkedin.pinot.core.startree.StarTree;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class StarTreeLoader {

  public static List<StarTreeV2> load(File indexDir, ImmutableSegment obj) throws Exception {

    // segment
    SegmentMetadataImpl _segmentMetadataImpl = new SegmentMetadataImpl(indexDir);

    // star tree
    List<StarTreeV2Metadata> starTreeV2MetadataList = _segmentMetadataImpl.getStarTreeV2Metadata();
    List<StarTreeV2> starTreeV2DataSources = new ArrayList<>();

    if (starTreeV2MetadataList != null) {
      int starTreeId = 0;
      for (StarTreeV2Metadata metaData : starTreeV2MetadataList) {
        StarTreeV2DataSource dataSource = new StarTreeV2DataSource(_segmentMetadataImpl, metaData, indexDir);
        StarTree starTree = dataSource.loadStarTree(starTreeId);
        dataSource.loadColumnsDataSource(starTreeId, obj);
        Map<String, StarTreeV2DimensionDataSource> dimensionDataSourceMap = dataSource.getDimensionForwardIndexReader();
        Map<String, StarTreeV2AggfunColumnPairDataSource> metricAggfuncPairDataSourceMap =
            dataSource.getMetricRawIndexReader();

        StarTreeV2Impl impl =
            new StarTreeV2Impl(starTree, metaData, dimensionDataSourceMap, metricAggfuncPairDataSourceMap);
        starTreeV2DataSources.add(impl);
        starTreeId += 1;
      }
    }
    return starTreeV2DataSources;
  }
}