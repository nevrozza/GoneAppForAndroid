package com.example.goneappforandroid.compose.settings

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val Gone: ImageVector
get() {
    if (_gone != null) {
        return _gone!!
    }
    _gone = ImageVector.Builder(
        name = "Gone",
        defaultWidth = 110.87.dp,
        defaultHeight = 111.24.dp,
        viewportWidth = 110.87F,
        viewportHeight = 111.24F,
    ).path(
        fill = SolidColor(Color(0xFF000000)),
        fillAlpha = 1.0F,
        strokeAlpha = 1.0F,
        strokeLineWidth = 1.0F,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Miter,
        strokeLineMiter = 4.0F,
        pathFillType = PathFillType.NonZero,
    ) {
        moveTo(55.68F, 26.06F)
        curveToRelative(0.0F, 5.66F, 0.0F, 11.32F, 0.0F, 16.98F)
        curveToRelative(-0.01F, 7.96F, 4.2F, 12.31F, 12.26F, 12.37F)
        curveToRelative(11.49F, 0.08F, 22.98F, 0.06F, 34.47F, 0.02F)
        curveToRelative(9.02F, -0.03F, 8.46F, 1.66F, 7.37F, 9.06F)
        curveToRelative(-1.7F, 11.59F, -6.82F, 21.82F, -14.65F, 29.86F)
        curveToRelative(-8.08F, 8.29F, -18.58F, 13.39F, -30.4F, 15.57F)
        curveToRelative(-27.75F, 5.12F, -56.34F, -14.45F, -62.92F, -42.21F)
        curveTo(-4.28F, 42.03F, 11.41F, 12.06F, 37.77F, 3.52F)
        curveToRelative(3.78F, -1.22F, 7.67F, -2.27F, 11.59F, -2.83F)
        curveToRelative(6.91F, -0.99F, 6.16F, 1.96F, 6.23F, 5.88F)
        curveToRelative(0.12F, 6.49F, 0.03F, 12.99F, 0.03F, 19.48F)
        curveToRelative(0.02F, 0.0F, 0.04F, 0.0F, 0.06F, 0.0F)
        close()
    }.build()
    return _gone!!
}
private var _gone: ImageVector? = null

@Preview
@Composable
@Suppress("UnusedPrivateMember")
private fun IconGonePreview() {
    Image(imageVector = Gone, contentDescription = null)
}