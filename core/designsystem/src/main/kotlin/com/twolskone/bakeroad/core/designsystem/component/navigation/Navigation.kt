package com.twolskone.bakeroad.core.designsystem.component.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

//private val NavigationBarHeight = 66.dp
private val NavigationBarShape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
private val NavigationBarElevation = 12.dp

@Composable
fun BakeRoadNavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = BakeRoadNavigationDefaults.navigationContainerColor,
    contentColor: Color = BakeRoadNavigationDefaults.navigationContentColor,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        color = containerColor,
        contentColor = contentColor,
        shape = NavigationBarShape,
        shadowElevation = NavigationBarElevation
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
//                .heightIn(min = NavigationBarHeight)
                .padding(horizontal = 16.dp)
                .padding(top = 4.dp, bottom = 6.dp)
                .navigationBarsPadding()
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}

private const val ItemAnimationDurationMillis = 100

@Composable
fun RowScope.BakeRoadNavigationBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .weight(1f)
            .padding(bottom = 7.dp)
            .clip(RoundedCornerShape(20.dp))
            .selectable(selected = selected, onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val color = if (selected) BakeRoadNavigationDefaults.navigationSelectedItemColor else LocalContentColor.current
        val iconColor by animateColorAsState(
            targetValue = color,
            animationSpec = tween(ItemAnimationDurationMillis)
        )
        val fontWeight by animateIntAsState(
            targetValue = if (selected) FontWeight.SemiBold.weight else FontWeight.Medium.weight,
            animationSpec = tween(ItemAnimationDurationMillis)
        )
        val textColor by animateColorAsState(
            targetValue = color,
            animationSpec = tween(ItemAnimationDurationMillis)
        )
        val style = BakeRoadTheme.typography.body2XsmallMedium.copy(
            fontWeight = FontWeight(fontWeight)
        )

        AnimatedContent(
            targetState = selected,
            transitionSpec = {
                fadeIn(tween(ItemAnimationDurationMillis)) togetherWith fadeOut(tween(ItemAnimationDurationMillis))
            }
        ) { selected ->
            CompositionLocalProvider(LocalContentColor provides iconColor) {
                if (selected) selectedIcon() else icon()
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        CompositionLocalProvider(
            LocalContentColor provides textColor,
            LocalTextStyle provides style,
            content = label
        )
    }
}

/**
 * 빵글 Navigation default values.
 */
object BakeRoadNavigationDefaults {

    val navigationContainerColor
        @Composable
        get() = BakeRoadTheme.colorScheme.White

    val navigationContentColor
        @Composable
        get() = BakeRoadTheme.colorScheme.Gray300

    val navigationSelectedItemColor
        @Composable
        get() = BakeRoadTheme.colorScheme.Primary500
}

@Preview
@Composable
private fun BakeRoadNavigationBarPreview() {
    BakeRoadTheme {
        var selectedIndex by remember { mutableIntStateOf(0) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BakeRoadTheme.colorScheme.White),
            contentAlignment = Alignment.BottomCenter
        ) {
            BakeRoadNavigationBar(modifier = Modifier.fillMaxWidth()) {
                BakeRoadNavigationBarItem(
                    selected = selectedIndex == 0,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "홈")
                    },
                    onClick = {
                        selectedIndex = 0
                    }
                )
                BakeRoadNavigationBarItem(
                    selected = selectedIndex == 1,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "검색")
                    },
                    onClick = {
                        selectedIndex = 1
                    }
                )
                BakeRoadNavigationBarItem(
                    selected = selectedIndex == 2,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = null
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "내 빵집")
                    },
                    onClick = {
                        selectedIndex = 2
                    }
                )
                BakeRoadNavigationBarItem(
                    selected = selectedIndex == 3,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "나의 빵글")
                    },
                    onClick = {
                        selectedIndex = 3
                    }
                )
            }
        }
    }
}