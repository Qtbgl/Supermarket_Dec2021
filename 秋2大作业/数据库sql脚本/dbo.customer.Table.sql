USE [supermarket]
GO
/****** Object:  Table [dbo].[customer]    Script Date: 2022/1/2 15:50:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[customer](
	[customer_id] [int] IDENTITY(3175,1) NOT NULL,
	[customer_name] [varchar](50) NOT NULL,
	[customer_pwd] [varchar](50) NOT NULL,
	[customer_vip] [int] NOT NULL,
	[logout] [int] NOT NULL,
 CONSTRAINT [PK_customer] PRIMARY KEY CLUSTERED 
(
	[customer_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[customer] ON 

INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip], [logout]) VALUES (3175, N'小明', N'12uufd', 0, 0)
INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip], [logout]) VALUES (3176, N'小乌', N'fmk322', 0, 0)
INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip], [logout]) VALUES (3180, N'小吴', N'23bbf2', 0, 0)
INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip], [logout]) VALUES (3185, N'小乐', N'4ttf4v', 0, 0)
INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip], [logout]) VALUES (3186, N'小向', N'5feck2', 0, 0)
INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip], [logout]) VALUES (3189, N'小晓', N'5fdj36', 0, 0)
INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip], [logout]) VALUES (3190, N'小翁', N'dgg5dv', 0, 0)
INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip], [logout]) VALUES (3191, N'小白', N'vf43gd', 0, 0)
INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip], [logout]) VALUES (3193, N'小云', N'32c3g3', 0, 0)
INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip], [logout]) VALUES (3194, N'小楚', N'23f321', 0, 0)
INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip], [logout]) VALUES (3196, N'小山', N'sss234', 0, 0)
INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip], [logout]) VALUES (3197, N'小谢', N'svf322', 0, 1)
INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip], [logout]) VALUES (3198, N'小张', N'5g32gc', 0, 1)
INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip], [logout]) VALUES (3199, N'小杨', N'vf2r2r', 0, 0)
INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip], [logout]) VALUES (3200, N'小曾', N'frvvf2', 0, 0)
INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip], [logout]) VALUES (3201, N'小迟', N'f4g422', 0, 0)
INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip], [logout]) VALUES (3202, N'小徐', N'25511f', 0, 0)
INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip], [logout]) VALUES (3203, N'小子', N'2324cc', 0, 0)
SET IDENTITY_INSERT [dbo].[customer] OFF
GO
ALTER TABLE [dbo].[customer] ADD  CONSTRAINT [DF_customer_customer_vip]  DEFAULT ((0)) FOR [customer_vip]
GO
ALTER TABLE [dbo].[customer] ADD  CONSTRAINT [DF_customer_logout]  DEFAULT ((0)) FOR [logout]
GO
ALTER TABLE [dbo].[customer]  WITH CHECK ADD  CONSTRAINT [CK_customer_logout] CHECK  (([logout]>=(0) AND [logout]<=(1)))
GO
ALTER TABLE [dbo].[customer] CHECK CONSTRAINT [CK_customer_logout]
GO
ALTER TABLE [dbo].[customer]  WITH CHECK ADD  CONSTRAINT [CK_customer_pwd] CHECK  ((len([customer_pwd])=(6)))
GO
ALTER TABLE [dbo].[customer] CHECK CONSTRAINT [CK_customer_pwd]
GO
ALTER TABLE [dbo].[customer]  WITH CHECK ADD  CONSTRAINT [CK_customer_vip] CHECK  (([customer_vip]>=(0) AND [customer_vip]<=(3)))
GO
ALTER TABLE [dbo].[customer] CHECK CONSTRAINT [CK_customer_vip]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0-正常用户，1-已注销' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'customer', @level2type=N'CONSTRAINT',@level2name=N'CK_customer_logout'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'规定顾客密码是6位字符串' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'customer', @level2type=N'CONSTRAINT',@level2name=N'CK_customer_pwd'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'规定vip最多3级' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'customer', @level2type=N'CONSTRAINT',@level2name=N'CK_customer_vip'
GO
