package com.example.goneappforandroid.compose.settings

import androidx.compose.foundation.Image
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val GitHub: ImageVector
    get() {
        if (_gitHub != null) {
            return _gitHub!!
        }
        _gitHub = ImageVector.Builder(
            name = "GitHub",
            defaultWidth = 1024.0.dp,
            defaultHeight = 1024.0.dp,
            viewportWidth = 1024.0F,
            viewportHeight = 1024.0F,
        ).materialPath {
            moveTo(511.6F, 76.3F)
            curveTo(264.3F, 76.2F, 64.0F, 276.4F, 64.0F, 523.5F)
            curveTo(64.0F, 718.9F, 189.3F, 885.0F, 363.8F, 946.0F)
            curveToRelative(23.5F, 5.9F, 19.9F, -10.8F, 19.9F, -22.2F)
            verticalLineToRelative(-77.5F)
            curveToRelative(-135.7F, 15.9F, -141.2F, -73.9F, -150.3F, -88.9F)
            curveTo(215.0F, 726.0F, 171.5F, 718.0F, 184.5F, 703.0F)
            curveToRelative(30.9F, -15.9F, 62.4F, 4.0F, 98.9F, 57.9F)
            curveToRelative(26.4F, 39.1F, 77.9F, 32.5F, 104.0F, 26.0F)
            curveToRelative(5.7F, -23.5F, 17.9F, -44.5F, 34.7F, -60.8F)
            curveToRelative(-140.6F, -25.2F, -199.2F, -111.0F, -199.2F, -213.0F)
            curveToRelative(0.0F, -49.5F, 16.3F, -95.0F, 48.3F, -131.7F)
            curveToRelative(-20.4F, -60.5F, 1.9F, -112.3F, 4.9F, -120.0F)
            curveToRelative(58.1F, -5.2F, 118.5F, 41.6F, 123.2F, 45.3F)
            curveToRelative(33.0F, -8.9F, 70.7F, -13.6F, 112.9F, -13.6F)
            curveToRelative(42.4F, 0.0F, 80.2F, 4.9F, 113.5F, 13.9F)
            curveToRelative(11.3F, -8.6F, 67.3F, -48.8F, 121.3F, -43.9F)
            curveToRelative(2.9F, 7.7F, 24.7F, 58.3F, 5.5F, 118.0F)
            curveToRelative(32.4F, 36.8F, 48.9F, 82.7F, 48.9F, 132.3F)
            curveToRelative(0.0F, 102.2F, -59.0F, 188.1F, -200.0F, 212.9F)
            arcToRelative(127.5F, 127.5F, 0.0F,
                isMoreThanHalf = false,
                isPositiveArc = true,
                dx1 = 38.1F,
                dy1 = 91.0F)
            verticalLineToRelative(112.5F)
            curveToRelative(0.8F, 9.0F, 0.0F, 17.9F, 15.0F, 17.9F)
            curveToRelative(177.1F, -59.7F, 304.6F, -227.0F, 304.6F, -424.1F)
            curveToRelative(0.0F, -247.2F, -200.4F, -447.3F, -447.5F, -447.3F)
            close()
        }.build()
        return _gitHub!!
    }
private var _gitHub: ImageVector? = null

@Preview
@Composable
@Suppress("UnusedPrivateMember")
private fun IconGitHubPreview() {
    Image(imageVector = GitHub, contentDescription = null)
}