package com.example.goneappforandroid.compose.settings
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val icClock: ImageVector
    get() {
        if (_icClock != null) {
            return _icClock!!
        }
        _icClock = ImageVector.Builder(
            name = "IcClock",
            defaultWidth = 73.0.dp,
            defaultHeight = 73.0.dp,
            viewportWidth = 73.0F,
            viewportHeight = 73.0F,
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
            moveTo(50.03F, 33.33F)
            curveToRelative(0.18F, 0.62F, 0.31F, 1.26F, 0.41F, 1.92F)
            curveToRelative(0.1F, 0.66F, 0.15F, 1.34F, 0.15F, 2.02F)
            curveToRelative(0.0F, 1.88F, -0.34F, 3.62F, -1.03F, 5.24F)
            reflectiveCurveToRelative(-1.62F, 3.02F, -2.82F, 4.21F)
            curveToRelative(-1.19F, 1.19F, -2.6F, 2.13F, -4.21F, 2.8F)
            curveToRelative(-1.61F, 0.67F, -3.35F, 1.01F, -5.2F, 1.01F)
            reflectiveCurveToRelative(-3.62F, -0.34F, -5.24F, -1.01F)
            curveToRelative(-1.61F, -0.67F, -3.02F, -1.61F, -4.21F, -2.8F)
            curveToRelative(-1.19F, -1.19F, -2.13F, -2.6F, -2.8F, -4.21F)
            curveToRelative(-0.67F, -1.61F, -1.01F, -3.36F, -1.01F, -5.24F)
            reflectiveCurveToRelative(0.34F, -3.59F, 1.01F, -5.2F)
            curveToRelative(0.67F, -1.61F, 1.61F, -3.02F, 2.8F, -4.21F)
            curveToRelative(1.19F, -1.19F, 2.6F, -2.13F, 4.21F, -2.82F)
            curveToRelative(1.61F, -0.69F, 3.36F, -1.03F, 5.24F, -1.03F)
            curveToRelative(1.44F, 0.0F, 2.79F, 0.21F, 4.06F, 0.63F)
            curveToRelative(1.27F, 0.42F, 2.46F, 0.98F, 3.56F, 1.69F)
            curveToRelative(0.24F, 0.15F, 0.38F, 0.36F, 0.41F, 0.63F)
            reflectiveCurveToRelative(-0.04F, 0.51F, -0.22F, 0.73F)
            curveToRelative(-0.18F, 0.22F, -0.4F, 0.35F, -0.66F, 0.4F)
            curveToRelative(-0.27F, 0.04F, -0.52F, -0.01F, -0.76F, -0.17F)
            curveToRelative(-0.93F, -0.57F, -1.93F, -1.04F, -3.0F, -1.39F)
            curveToRelative(-1.07F, -0.35F, -2.2F, -0.53F, -3.4F, -0.53F)
            curveToRelative(-3.2F, 0.0F, -5.88F, 1.08F, -8.04F, 3.23F)
            reflectiveCurveToRelative(-3.23F, 4.83F, -3.23F, 8.04F)
            reflectiveCurveToRelative(1.08F, 5.88F, 3.23F, 8.04F)
            reflectiveCurveToRelative(4.83F, 3.23F, 8.04F, 3.23F)
            reflectiveCurveToRelative(5.88F, -1.08F, 8.04F, -3.23F)
            curveToRelative(2.15F, -2.15F, 3.23F, -4.83F, 3.23F, -8.04F)
            curveToRelative(0.0F, -0.55F, -0.03F, -1.08F, -0.1F, -1.59F)
            reflectiveCurveToRelative(-0.17F, -1.02F, -0.3F, -1.52F)
            curveToRelative(-0.07F, -0.27F, -0.05F, -0.53F, 0.05F, -0.8F)
            curveToRelative(0.1F, -0.27F, 0.27F, -0.45F, 0.51F, -0.56F)
            reflectiveCurveToRelative(0.49F, -0.12F, 0.75F, -0.03F)
            reflectiveCurveToRelative(0.43F, 0.28F, 0.51F, 0.56F)

            moveTo(34.68F, 42.61F)
            lineToRelative(-4.04F, -4.08F)
            curveToRelative(-0.2F, -0.2F, -0.3F, -0.44F, -0.3F, -0.73F)
            reflectiveCurveToRelative(0.11F, -0.54F, 0.33F, -0.76F)
            curveToRelative(0.2F, -0.2F, 0.44F, -0.3F, 0.73F, -0.3F)
            reflectiveCurveToRelative(0.54F, 0.1F, 0.76F, 0.3F)
            lineToRelative(3.22F, 3.25F)
            lineToRelative(12.99F, -12.99F)
            curveToRelative(0.2F, -0.2F, 0.44F, -0.3F, 0.73F, -0.31F)
            curveToRelative(0.29F, -0.01F, 0.54F, 0.09F, 0.76F, 0.31F)
            curveToRelative(0.22F, 0.22F, 0.33F, 0.48F, 0.33F, 0.76F)
            curveToRelative(0.0F, 0.29F, -0.11F, 0.54F, -0.33F, 0.76F)
            lineToRelative(-13.79F, 13.79F)
            curveToRelative(-0.2F, 0.2F, -0.44F, 0.3F, -0.71F, 0.3F)
            reflectiveCurveToRelative(-0.5F, -0.1F, -0.68F, -0.3F)
            close()
        }.path(
            fill = SolidColor(Color(0xFF000000)),
            fillAlpha = 1.0F,
            strokeAlpha = 1.0F,
            strokeLineWidth = 1.0F,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Miter,
            strokeLineMiter = 4.0F,
            pathFillType = PathFillType.NonZero,
        ) {
            moveTo(27.22F, 22.1F)
            curveToRelative(0.23F, -0.2F, 0.49F, -0.3F, 0.8F, -0.3F)
            reflectiveCurveToRelative(0.57F, 0.11F, 0.8F, 0.34F)
            curveToRelative(0.23F, 0.23F, 0.34F, 0.5F, 0.34F, 0.82F)
            reflectiveCurveToRelative(-0.11F, 0.58F, -0.34F, 0.78F)
            lineToRelative(-4.63F, 4.44F)
            curveToRelative(-0.23F, 0.2F, -0.49F, 0.3F, -0.8F, 0.3F)
            reflectiveCurveToRelative(-0.57F, -0.11F, -0.8F, -0.34F)
            curveToRelative(-0.23F, -0.23F, -0.34F, -0.5F, -0.34F, -0.82F)
            reflectiveCurveToRelative(0.11F, -0.58F, 0.34F, -0.78F)
            lineToRelative(4.63F, -4.44F)
            close()
        }.build()
        return _icClock!!
    }
private var _icClock: ImageVector? = null

@Preview
@Composable
@Suppress("UnusedPrivateMember")
private fun IconIcClockPreview() {
    Image(imageVector = icClock, contentDescription = null)
}