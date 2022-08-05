package cn.usfl.calculator.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import cn.usfl.calculator.R
import cn.usfl.calculator.ui.theme.Gap
import cn.usfl.calculator.ui.theme.colors
import cn.usfl.calculator.ui.widgets.StaggeredVerticalGrid


@Composable
fun CalculatorScreen() {
    //当前计算的算式
    val formula = remember { mutableStateOf("") }
    //历史记录
    val history = remember { mutableStateListOf<String>() }
    Column(
        Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        //标题栏
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 22.sp,
            color = colors.onBackground,
            modifier = Modifier.padding(horizontal = Gap.Big, vertical = Gap.Mid)
        )
        //计算区域
        LazyColumn(
            Modifier
                .background(colors.surface)
                .fillMaxWidth()
                .weight(1f),
            reverseLayout = true,
            contentPadding = PaddingValues(horizontal = Gap.Big, vertical = Gap.Big)
        ) {
            item {
                Text(
                    text = formula.value,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    fontSize = 60.sp,
                    color = colors.onBackground,
                    fontWeight = FontWeight.Bold
                )
            }
            items(history) {
                Text(
                    text = it,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    fontSize = 16.sp,
                    color = colors.onSecondary,
                    fontWeight = FontWeight.Normal
                )
            }
        }
        //按键区域
        StaggeredVerticalGrid(maxRows = 4) {
            buttons.forEach {
                CalculateButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(it.text),
                    fontColor = when (it.fontColor) {
                        ColorType.Accent -> colors.primary//文字强调色
                        ColorType.Normal -> colors.onBackground
                        ColorType.OnAccent -> colors.onPrimary
                    },
                    backgroundColor = when (it.backgroundColor) {
                        ColorType.Accent -> colors.primary
                        else -> colors.background
                    },
                ) {
                    it.onClick(formula) { new ->
                        //提交计算结果，将其纳入历史记录
                        if (new != null) {
                            history.add(new)
                        } else {
                            //清空历史记录
                            history.clear()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalculateButton(
    modifier: Modifier,
    text: String,
    fontColor: Color = colors.onBackground,
    backgroundColor: Color = colors.background,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        Modifier
            .clickable(
                onClickLabel = text,
                onClick = onClick,
                indication = null,
                interactionSource = interactionSource
            )
            .then(modifier), contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .padding(horizontal = Gap.Big, vertical = Gap.Mid)
                .aspectRatio(1f, true)
                .clip(RoundedCornerShape(50))
                .background(
                    backgroundColor, RoundedCornerShape(50)
                )
                .clickable(
                    onClickLabel = text,
                    onClick = onClick,
                    indication = LocalIndication.current,
                    interactionSource = interactionSource
                ), contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = fontColor,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(
    device = "spec:width=1440px,height=3040px,dpi=560,isRound=true",
    backgroundColor = 0xFFFFFFFF,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun CalculatorScreenPreview() {
    CalculatorScreen()
}