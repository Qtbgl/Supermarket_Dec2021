USE [supermarket]
GO
/****** Object:  Table [dbo].[import]    Script Date: 2022/1/2 15:50:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[import](
	[import_id] [int] IDENTITY(4121,1) NOT NULL,
	[goods_id] [int] NOT NULL,
	[import_date] [datetime] NULL,
	[number_a] [int] NOT NULL,
 CONSTRAINT [PK_import] PRIMARY KEY CLUSTERED 
(
	[import_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[import] ON 

INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4122, 1223, NULL, 30)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4124, 1224, NULL, 42)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4126, 1226, NULL, 50)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4127, 1229, NULL, 20)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4128, 1230, NULL, 43)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4129, 1232, NULL, 32)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4130, 1233, NULL, 15)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4131, 1234, NULL, 23)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4132, 1235, NULL, 54)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4133, 1236, NULL, 34)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4134, 1238, NULL, 43)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4135, 1239, NULL, 32)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4136, 1241, NULL, 65)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4137, 1242, NULL, 43)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4138, 1243, NULL, 42)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4139, 1244, NULL, 32)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4140, 1245, NULL, 32)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4141, 1246, NULL, 23)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4142, 1247, NULL, 34)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4143, 1248, NULL, 52)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4144, 1249, NULL, 37)
SET IDENTITY_INSERT [dbo].[import] OFF
GO
ALTER TABLE [dbo].[import]  WITH CHECK ADD  CONSTRAINT [FK_import_goods] FOREIGN KEY([goods_id])
REFERENCES [dbo].[goods] ([goods_id])
GO
ALTER TABLE [dbo].[import] CHECK CONSTRAINT [FK_import_goods]
GO
