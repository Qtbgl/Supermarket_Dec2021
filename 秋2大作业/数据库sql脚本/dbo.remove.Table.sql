USE [supermarket]
GO
/****** Object:  Table [dbo].[remove]    Script Date: 2022/1/2 15:50:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[remove](
	[remove_id] [int] IDENTITY(6043,1) NOT NULL,
	[goods_id] [int] NOT NULL,
	[remove_date] [datetime] NULL,
	[number_o] [int] NOT NULL,
 CONSTRAINT [PK_remove] PRIMARY KEY CLUSTERED 
(
	[remove_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[remove] ON 

INSERT [dbo].[remove] ([remove_id], [goods_id], [remove_date], [number_o]) VALUES (6046, 1233, NULL, 10)
SET IDENTITY_INSERT [dbo].[remove] OFF
GO
ALTER TABLE [dbo].[remove]  WITH CHECK ADD  CONSTRAINT [FK_remove_goods] FOREIGN KEY([goods_id])
REFERENCES [dbo].[goods] ([goods_id])
GO
ALTER TABLE [dbo].[remove] CHECK CONSTRAINT [FK_remove_goods]
GO
