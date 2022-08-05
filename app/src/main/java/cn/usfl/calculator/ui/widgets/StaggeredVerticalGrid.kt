package cn.usfl.calculator.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout


@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    maxRows: Int,
    maxColumns: Int,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurableList, constraints ->
        check(constraints.hasBoundedWidth) {
            "Unbounded width not supported"
        }
        val columnWidth = constraints.maxWidth / maxRows
        val columnHeight = constraints.maxHeight / maxColumns
        val itemConstraints = constraints.copy(
            minWidth = 0,
            maxWidth = columnWidth,
            minHeight = 0,
            maxHeight = columnHeight
        )
        val placeableList = measurableList.map { measurable ->
            measurable.measure(itemConstraints)
        }
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            placeableList.forEachIndexed { index, placeable ->
                placeable.place(
                    x = columnWidth * (index % maxRows),
                    y = columnHeight * (index / maxRows)
                )
            }
        }
    }
}