USE [supermarket]
GO
/****** Object:  Table [dbo].[goods]    Script Date: 2022/1/2 15:50:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[goods](
	[goods_id] [int] IDENTITY(1223,1) NOT NULL,
	[goods_name] [varchar](50) NOT NULL,
	[goods_type] [varchar](50) NOT NULL,
	[number_c] [int] NOT NULL,
	[logout] [int] NOT NULL,
 CONSTRAINT [PK_goods] PRIMARY KEY CLUSTERED 
(
	[goods_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[goods] ON 

INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1223, N'红茶', N'饮料', 30, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1224, N'农夫山泉', N'饮料', 42, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1226, N'火腿肠', N'日用食品', 45, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1229, N'瓜子', N'休闲食品', 20, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1230, N'毛巾', N'洗漱用品', 43, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1232, N'雪碧', N'饮料', 32, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1233, N'可口可乐', N'饮料', 25, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1234, N'青岛啤酒', N'饮料', 23, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1235, N'洗面奶', N'日用品', 54, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1236, N'味精', N'调味品', 34, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1238, N'薯片', N'休闲食品', 43, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1239, N'白砂糖', N'调味品', 32, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1241, N'开心果', N'休闲食品', 65, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1242, N'香皂', N'洗漱用品', 43, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1243, N'酱油', N'调味品', 42, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1244, N'洗发液', N'日用品', 32, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1245, N'沐浴乳', N'日用品', 32, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1246, N'花生油', N'调味品', 23, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1247, N'面包', N'日用食品', 34, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1248, N'方便面', N'日用食品', 52, 0)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c], [logout]) VALUES (1249, N'花生', N'休闲食品', 37, 0)
SET IDENTITY_INSERT [dbo].[goods] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [C2]    Script Date: 2022/1/2 15:50:52 ******/
ALTER TABLE [dbo].[goods] ADD  CONSTRAINT [C2] UNIQUE NONCLUSTERED 
(
	[goods_name] ASC,
	[goods_type] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[goods] ADD  CONSTRAINT [DF_goods_logout]  DEFAULT ((0)) FOR [logout]
GO
ALTER TABLE [dbo].[goods]  WITH CHECK ADD  CONSTRAINT [CK_goods_logout] CHECK  (([logout]>=(0) AND [logout]<=(1)))
GO
ALTER TABLE [dbo].[goods] CHECK CONSTRAINT [CK_goods_logout]
GO
ALTER TABLE [dbo].[goods]  WITH CHECK ADD  CONSTRAINT [CK_goods_number_c] CHECK  (([number_c]>=(0)))
GO
ALTER TABLE [dbo].[goods] CHECK CONSTRAINT [CK_goods_number_c]
GO
