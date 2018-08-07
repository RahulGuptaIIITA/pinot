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

import java.util.Map;


/**
 * Helper class to represent a tree node.
 */
class TreeNode {
  int _startDocId = -1;
  int _endDocId = -1;
  int _dimensionId = -1;
  int _dimensionValue = -1;
  int _childDimensionId = -1;
  int _aggDataDocumentId = -1;
  Map<Integer, TreeNode> _children = null;
}