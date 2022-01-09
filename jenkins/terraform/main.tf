terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.27"
    }
  }

  required_version = ">= 0.14.9"
}

data "template_file" "user_data" {
  template = file("terraform.yaml")
}

provider "aws" {
  profile                 = "default"
  region                  = "us-east-2"
  access_key = "AKIA5HPHYM2IHU57ZD7Q"
  secret_key = "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"
}

resource "aws_instance" "app_server" {
  ami           = "ami-0d97ef13c06b05a19"
  instance_type = "t2.micro"
  user_data = data.template_file.user_data.rendered
  key_name = "ssh_Grilly"
  security_groups = [aws_security_group.allow_tls.name]
  associate_public_ip_address = true

  tags = {
    Name = "app"
    groups = "app"
    owner  = "Grilly-Jordan"
  }
}


resource "aws_key_pair" "deployer" {
  key_name = "ssh_Grilly"
  public_key = "ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIEVLkgbIbcYQElcrSgt2tZDcjxlnGAjUjrM4iseFod9P jordangrilly@gmail.com"
}

resource "aws_security_group" "allow_tls" {
  name = "Grilly_allow_tls"
  //vpc_id      = aws_vpc.main.id

  ingress {
    description      = "ssh"
    from_port        = 22
    to_port          = 22
    protocol         = "tcp"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
    //ipv6_cidr_blocks = [aws_vpc.main.ipv6_cidr_block]
  }

  ingress {
    description      = "ssh"
    from_port        = 8081
    to_port          = 8081
    protocol         = "tcp"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
    //ipv6_cidr_blocks = [aws_vpc.main.ipv6_cidr_block]
  }

  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
    //ipv6_cidr_blocks = ["::/0"]
  }

  tags = {
    Name = "Grilly_allow_tls"
  }
}
output "instance_id" {
  description = "ID of the EC2 instance"
  value       = aws_instance.app_server.*.id
}

output "instance_public_ip" {
  description = "Public IP address of the EC2 instance"
  value       = aws_instance.app_server.*.public_ip
}

output userdata {
  value = "\n${data.template_file.user_data.rendered}"
}